package org.revo.Service.Impl;

import org.revo.Domain.User;
import org.revo.Repository.UserRepository;
import org.revo.Service.CloudinaryService;
import org.revo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by ashraf on 17/02/17.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private CloudinaryService cloudinaryService;
    @PersistenceContext
    private EntityManager em;

    @Override
    public User save(User user) {
        if (user.getPassword() != null && !user.getPassword().trim().isEmpty() && user.getPassword().length() != 60)
            user.setPassword(encoder.encode(user.getPassword()));
        if (user.getImage() != null && !user.getImage().isEmpty()) {
            user.setImageUrl(cloudinaryService.saveImage(user.getImage()));
        }
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void update(User user) throws Exception {
        User current = current();
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaUpdate<User> update = cb.createCriteriaUpdate(User.class);
        Root e = update.from(User.class);
        if (!encoder.matches(user.getCurrentPassword(), current.getPassword())) throw new Exception();
        if (user.getInfo() != null && !user.getInfo().trim().isEmpty() && !Objects.equals(current.getInfo(), user.getInfo()))
            update.set("info", user.getInfo());
        if (user.getName() != null && !user.getName().trim().isEmpty() && !Objects.equals(current.getName(), user.getName()))
            update.set("name", user.getName());
        if (user.getPhone() != null && !user.getPhone().trim().isEmpty() && !Objects.equals(current.getPhone(), user.getPhone()))
            update.set("phone", user.getPhone());
        if (user.getPassword() != null && !user.getPassword().trim().isEmpty() && !encoder.matches(current.getPassword(), user.getPassword()))
            update.set("password", encoder.encode(user.getPassword()));
        if (user.getImage() != null && !user.getImage().isEmpty())
            update.set("imageUrl", cloudinaryService.saveImage(user.getImage()));
        update.where(cb.equal(e.get("id"), current.getId()));
        this.em.createQuery(update).executeUpdate();
    }

    @Override
    public User current() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User)
            return (User) authentication.getPrincipal();
        else
            return new User();
    }

    @Override
    public User user(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Override
    public void active(Long id) {
        userRepository.setActiveTrueAndType110ById(id);
    }

    @Override
    public long count() {
        return userRepository.count();
    }
}

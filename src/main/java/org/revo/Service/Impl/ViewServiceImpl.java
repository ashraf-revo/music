package org.revo.Service.Impl;

import org.revo.Domain.View;
import org.revo.Repository.ViewRepository;
import org.revo.Service.UserService;
import org.revo.Service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by ashraf on 17/02/17.
 */
@Service
public class ViewServiceImpl implements ViewService {
    @Autowired
    private ViewRepository viewRepository;
    @Autowired
    private UserService userService;

    @Override
    public View view(View view) {
        view.setUser(userService.current());
        Assert.notNull(view.getUser().getId());
        Assert.notNull(view.getSong().getId());
        Assert.isNull(view.getId());
        return viewRepository.save(view);
    }

    @Override
    public List<View> readBySong_Id(Long id) {
        return viewRepository.readBySong_Id(id);
    }

    @Override
    public List<View> readByUser_Id(Long id) {
        return viewRepository.readByUser_Id(id);
    }
}

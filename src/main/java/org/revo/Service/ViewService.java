package org.revo.Service;

import org.revo.Domain.View;

import java.util.List;

/**
 * Created by ashraf on 22/01/17.
 */
public interface ViewService {
    View view(View view);

    List<View> readBySong_Id(Long id);

    List<View> readByUser_Id(Long id);
}
package com.gdaniel.glpayroll.jwt.model;

import java.util.List;
import java.util.Set;
import com.gdaniel.glpayroll.abstractarea.entities.UsersRolesEntity;
import com.gdaniel.glpayroll.adminarea.user.entity.UserEntity;
import java.util.ArrayList;
import java.util.HashSet;

public class UserPrincipa2 extends UserPrincipal {

    public UserPrincipa2(UserEntity userEntity) {
        super(userEntity);

    }

    public Set<String> getRoles() {

        List<UsersRolesEntity> list = this.userEntity.getLinkedroles();
        List<String> slist = new ArrayList<String>();

        list.forEach(item -> {
            String rname = item.getRole().getRoleName();
            slist.add(rname);

        });

        return new HashSet<>(slist);
    }

}

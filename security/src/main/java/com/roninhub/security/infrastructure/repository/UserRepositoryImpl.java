package com.roninhub.security.infrastructure.repository;

import com.roninhub.security.domain.auth.constant.PermissionName;
import com.roninhub.security.domain.auth.constant.RoleName;
import com.roninhub.security.domain.auth.entity.Permission;
import com.roninhub.security.domain.auth.entity.Role;
import com.roninhub.security.domain.auth.entity.User;
import com.roninhub.security.domain.auth.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Set;

@Repository
public class UserRepositoryImpl implements UserRepository {
    public static final Map<String, User> USER_DATA;

    // Fake data
    static {
        // Permissions
        var postFreeView = new Permission(1L, PermissionName.POST_FREE_VIEW);
        var postFreeEdit = new Permission(2L, PermissionName.POST_FREE_EDIT);
        var postPremiumView = new Permission(3L, PermissionName.POST_PREMIUM_VIEW);
        var postPremiumEdit = new Permission(4L, PermissionName.POST_PREMIUM_EDIT);

        // Roles
        var adminRole = new Role(1L, RoleName.ADMIN, Set.of(postFreeView, postFreeEdit, postPremiumView, postPremiumEdit));
        var editorRole = new Role(2L, RoleName.EDITOR, Set.of(postFreeView, postFreeEdit, postPremiumView, postPremiumEdit));
        var userFreeRole = new Role(2L, RoleName.USER_FREE, Set.of(postFreeView));
        var userPremiumRole = new Role(2L, RoleName.USER_PREMIUM, Set.of(postFreeView, postPremiumView));
        var anomymousRole = new Role(2L, RoleName.ANONYMOUS, Set.of(postFreeView));

        // Users
        var roninFree1 = new User(1L, "roninFree1", "123456", Set.of(userFreeRole, anomymousRole));
        var roninFree2 = new User(2L, "roninFree2", "123456", Set.of(userFreeRole));

        var roninPremium1 = new User(3L, "roninPremium1", "123456", Set.of(userFreeRole, anomymousRole, userPremiumRole));
        var roninPremium2 = new User(4L, "roninPremium2", "123456", Set.of(userFreeRole, userPremiumRole));

        var roninEditor = new User(5L, "roninEditor", "123456", Set.of(userFreeRole, anomymousRole, userPremiumRole, editorRole));

        var roninAdmin = new User(6L, "roninAdmin", "123456", Set.of(adminRole));

        USER_DATA = Map.of(
                "roninFree1", roninFree1,
                "roninFree2", roninFree2,
                "roninPremium1", roninPremium1,
                "roninPremium2", roninPremium2,
                "roninEditor", roninEditor,
                "roninAdmin", roninAdmin
        );
    }

    @Override
    public User findByUsername(String username) {
        return USER_DATA.get(username);
    }
}

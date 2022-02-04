package ru.vadim.hostel.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static ru.vadim.hostel.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    MANAGER(Sets.newHashSet(
            GUEST_CREATE,
            GUEST_READ,
            GUEST_UPDATE,
            GUEST_DELETE,
            APARTMENT_READ,
            CATEGORY_READ

    )),
    ADMIN(Sets.newHashSet(
            CATEGORY_CREATE,
            CATEGORY_READ,
            CATEGORY_UPDATE,
            CATEGORY_DELETE,
            APARTMENT_READ,
            APARTMENT_CREATE,
            APARTMENT_UPDATE,
            APARTMENT_DELETE,
            GUEST_CREATE,
            GUEST_READ,
            GUEST_UPDATE,
            GUEST_DELETE,
            MANAGER_CREATE,
            MANAGER_READ,
            MANAGER_UPDATE,
            MANAGER_DELETE
    ));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getSimpleGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }

}

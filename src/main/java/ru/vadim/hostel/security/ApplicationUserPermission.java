package ru.vadim.hostel.security;

public enum ApplicationUserPermission {
    APARTMENT_READ("apartment:read"),
    APARTMENT_UPDATE("apartment:update"),
    APARTMENT_CREATE("apartment:create"),
    APARTMENT_DELETE("apartment:delete"),
    CATEGORY_UPDATE("category:update"),
    CATEGORY_READ("category:read"),
    CATEGORY_CREATE("category:create"),
    CATEGORY_DELETE("category:delete"),
    GUEST_READ("guest:read"),
    GUEST_UPDATE("guest:update"),
    GUEST_CREATE("guest:create"),
    GUEST_DELETE("guest:delete"),
    MANAGER_CREATE("manager:create"),
    MANAGER_READ("manager:read"),
    MANAGER_UPDATE("manager:update"),
    MANAGER_DELETE("manager:delete"),
    USER_CREATE("user:create"),
    USER_READ("user:read"),
    USER_UPDATE("user:update"),
    USER_DELETE("user:delete");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}

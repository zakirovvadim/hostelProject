package ru.vadim.hostel.config;

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
    MANAGER_READ("manager:create"),
    MANAGER_UPDATE("manager:create"),
    MANAGER_DELETE("manager:create");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}

package net.ivanz.staffAccountingSystem.utils;


import net.ivanz.staffAccountingSystem.integration.UserRoles;

import java.util.List;

public class AuthHelper {
    private AuthHelper() {
    }

    public static String[] convertToAuthorities(List<UserRoles> grantedAuthorities) {
        return grantedAuthorities.stream().map(Enum::name).toArray(String[]::new);
    }

    public static String[] convertToAuthorities(UserRoles grantedAuthorities) {
        return new String[] {grantedAuthorities.name()};
    }
}


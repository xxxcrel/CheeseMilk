package cn.qisee.cheesemilk.security.acl;

public enum AclPermission {
        READ("read"),
        WRITE("write"),
        CREATE("create"),
        DELETE("delete"),
        ADMIN("admin");

        private final String name;

        AclPermission(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
}

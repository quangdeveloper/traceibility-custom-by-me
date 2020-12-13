package vn.vnpt.tracebility_custom.type;

public enum UserTypeEnum {
    ADMIN("Quản trị viên", "ADMIN", 0),
    EMPLOYEE("Nhân viên", "employee", 1),
    CUSTOMER("Khách", "customer", 2),

    ;

    private final String title;
    private final String name;
    private final Integer value;

    UserTypeEnum(String title, String name, Integer value) {
        this.title = title;
        this.name = name;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }

    public static UserTypeEnum parse(String name, Integer type) {

        for (UserTypeEnum b : UserTypeEnum.values()) {
            if (b.value.equals(type) || b.toString().equals(name)) {
                return b;
            }
        }

        return null;
    }

    public static UserTypeEnum parse(Integer type) {

        for (UserTypeEnum b : UserTypeEnum.values()) {
            if (b.value.equals(type)) {
                return b;
            }
        }

        return null;
    }
}

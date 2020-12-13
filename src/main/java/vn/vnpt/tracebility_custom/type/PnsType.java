package vn.vnpt.tracebility_custom.type;

public enum PnsType {

    ADMIN("Register", "Register", 0),
    EMPLOYEE("Confirm otp", "Confirm otp", 1),
    CUSTOMER("Notification", "Notification", 2),
    ;

    private final String title;
    private final String name;
    private final Integer value;

    PnsType(String title, String name, Integer value) {

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


    public static PnsType parse(String name, Integer type) {

        for (PnsType b : PnsType.values()) {

            if (b.value.equals(type) || b.toString().equals(name)) return b;
        }
        return null;
    }

    public static PnsType parse(Integer type) {

        for (PnsType b : PnsType.values()) {

            if (b.value.equals(type))  return b;
        }
        return null;
    }



}

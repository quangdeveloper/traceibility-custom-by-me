package vn.vnpt.tracebility_custom.type;


public enum ActionType {

    CONFIRM_PASSWORD("0", "CONFIRM_PASSWORD", "Confirm password"),
    ;
    private String id;
    private String code;
    private String name;

    ActionType(String id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }
}

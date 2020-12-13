package vn.vnpt.tracebility_custom.util;

public interface Constant {

    interface DatePaterm {
        String LOCAL_DATE = "dd/MM/yyyy";
        String LOCAL_DATE_CUSTOM = "yyyy-MM-dd";
    }

    interface COMMON {
        String MESSAGE_RESTORE = "Mã khôi phục của quý khách là: ";
        String NODE_MESSAGE_STORE = "Mã khôi phục chỉ có tác dụng trong vòng 3 phút";
        String IMG_FORMAT = "Ảnh không đúng định dạng";
        String IMG_SIZE = "Ảnh không đúng định dạng, dung lượng quá lớn";
        String SERVER_NOT_RECEIVED = "Server không nhận được dữ liệu";
    }

    interface RESPONSE {
        /**
         * lỗi này return ra 1 danh sách kết quả
         */
        String RS = "rs";

        interface CODE {

            String C006 = "006";

            String OK = "200";

            String C404 = "404";
            String C409 = "409";

            String C500 = "500";
            String C530 = "530";

            String C803 = "803";
            String C804 = "804";
            String C805 = "805";

            String ERROR = "999";

        }

        interface MESSAGE {

            String OK = "Thực hiện thành công";

            String C006 = "Upload  fail";

            String C007 = "Upload key has expired";

            String C404 = "Không tìm thấy dữ liệu";

            String C404_ROLE = "Không tìm thấy được quyền này";
            String C404_ROLE_NOT_EXISTS = "Quyền này không tồn tại";

            String C404_USER = "Không tìm thấy người dùng";
            String C404_USER_EXISTS = "Người dùng không tôn tại trong hệ thống";

            String C404_COURSE = "Khóa học chưa được tạo";

            String C404_MENU_ITEM = "Không tìm thấy menu item";

            String C404_MENU_ITEM_NOT_EXISTS = "Menu không tồn tại";

            String C409_USER_CUSTOM = "Tài khoản hoặc email hoặc số điện thoại đăng kí đã tồn tại";

            String C409_PHONE = "Số điện thoại đăng kí đã sử dụng";

            String C409_EMAIL = "Email đăng kí đã sử dụng";

            String C409_PASSPORT = "Passport đăng kí đã sử dụng";

            String C_530 = "Danh sách file gửi về rỗng";

            String C803 = "Người dùng chưa yêu cầu gửi mã xác nhận otp";

            String C804 = "Mã otp không đúng";

            String C805 = "Mã otp đã hết hạn";

            String ACCOUNT_NOT_EXISTS = "Tài khoản không tồn tại";

            String PASSWORD_WRONG = "Sai mật khẩu";

            String ERROR = "Lỗi hệ thống";
        }

        interface JSON_KEY {
            String RETURN_VALUE = "Return value";
            String UPLOAD_KEY = "PO_UPLOAD_KEY";
        }
    }
}

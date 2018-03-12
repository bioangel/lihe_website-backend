package com.common.exception;

public abstract class Assert {

    public static void isTrue(boolean expression, ErrorCode errorCode) {
        if (!expression) {
            throw new BadRequestException(errorCode);
        }
    }

    public static void isNull(Object object, ErrorCode errorCode) {
        if (object != null) {
            throw new BadRequestException(errorCode);
        }
    }

    public static void notNull(Object object, ErrorCode errorCode) {
        notNull(object, new BadRequestException(errorCode));
    }

    public static <T extends Throwable> void notNull(Object object, T exp) throws T {
        if (object == null) {
            throw exp;
        }
    }

}

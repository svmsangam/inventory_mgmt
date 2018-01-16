package com.inventory.core.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Created by dhiraj on 6/4/17.
 */

public class GlobalValidation {

    public String checkDouble(Double value, int min, int maxFraction, String target, boolean notNull) {

        if (notNull && value == null) {

            return "Cannot Be Null";
        }

        if (value != null) {

            if (value < min) {

                return "Must Be Greater Than" + min;
            }

            return checkFraction(value, maxFraction);
        }
        return "";
    }

    public String checkInteger(Integer value, int min, int max, String target, boolean notNull) {

        if (notNull && value == null) {

            return "Cannot Be null";
        }

        if (value != null) {

            if (value < min) {

                return "Must Be Greater Than " + min;
            }

            if (value > max) {

                return "Must Be Less Than " + max;
            }
        }

        return "";

    }

    public String checkLong(Long value, int min, String target, boolean notNull) {

        if (notNull && value == null) {

            return "Cannot Be null";
        }

        if (value != null) {

            if (value < min) {

                return "Must Be Greater Than " + min;
            }

        }

        return "";

    }

    public String checkString(String value, int minLength, int maxLength, String target, boolean notNull) {

        if (notNull && value == null) {

            return "Cannot Be Null";
        }

        if (value != null && !value.isEmpty()) {

            if ("".equals(value.trim())) {

                return "Cannot Be Blank";
            }

            if (minLength == maxLength) {

                if (value.length() != minLength) {

                    return "Exactly " + minLength + " length required";
                }

            } else {

                if (value.length() < minLength) {

                    return "Must Be Greater Than " + minLength + " length";
                }

                if (value.length() > maxLength) {

                    return "Must Be Less Than " + maxLength + " length";
                }
            }

        }
        return "";
    }

    public String checkDoubleGreaterThan(Double value, Double checker, String target, String checkerTarget, boolean isEqual) {

        if (value != null && checker != null) {

            if (isEqual) {

                if (value < checker | checker > value) {

                    return "Must be Greater or Equal Than " + checkerTarget;
                }
            } else {

                if (value < checker || Objects.equals(value, checker)) {

                    return "Must Be Greater Than " + checkerTarget;
                }
            }
        }

        return "";
    }

    public String checkIntegerGreaterThan(Integer value, Integer checker, String target, String checkerTarget, boolean isEqual) {

        if (value != null && checker != null) {

            if (isEqual) {

                if (value < checker) {

                    return "Must Be Greater or Equal Than " + checkerTarget;
                }
            } else {

                if (value < checker || Objects.equals(value, checker)) {

                    return "Must Be Greater Than " + checkerTarget;
                }
            }
        }

        return "";
    }

    public String checkDate(String dateStr, String target, boolean notNull, boolean isBefore, boolean isAfter) {

        if (notNull && dateStr == null) {

            return "Cannot Be Null";
        }

        if (dateStr != null) {

            if (dateStr.length() != 10 || !(dateStr.matches("\\d{4}-[01]\\d-[0-3]\\d"))) {

                return "invalid date";
            }

            return checkDateFormat(dateStr, isBefore, isAfter);
        }

        return "";
    }

    public String checkDate(Date dateStr, String target, boolean notNull, boolean isBefore, boolean isAfter) {

        if (notNull && dateStr == null) {

            return "Cannot Be Null";
        }

        return "";
    }


    private String checkFraction(double value, int maxFraction) {

        String aString = Double.toString(value);

        String[] fraction = aString.split("\\.");

        if (fraction.length == 2) {

            if (fraction[1].length() > maxFraction) {

                return "Floating Value Limit Exceeded!";
            }
        } else {

            return "Invalid Number Format is eg: 10.00 , 23.93 ";
        }

        return "";
    }

    private String checkDateFormat(String dateStr, boolean isBefore, boolean isAfter) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        df.setLenient(false);

        try {

            Date date = df.parse(dateStr);

            if (isBefore) {

                return checkDateIsBefore(date, df);
            }

            if (isAfter) {

                return checkDateIsAfter(date, df);

            }

        } catch (ParseException ex) {

            return "invalid date";
        }

        return "";
    }

    private String checkDateIsBefore(Date date, SimpleDateFormat df) {

        try {

            String currentDateStr = df.format(new Date());

            Date currentDate = df.parse(currentDateStr);

            if (!date.before(currentDate)) {

                return "date must be before current date";
            }
        } catch (Exception e) {

            return "date must be before current date";
        }

        return "";
    }

    private String checkDateIsAfter(Date date, SimpleDateFormat df) {

        try {

            String currentDateStr = df.format(new Date());

            Date currentDate = df.parse(currentDateStr);

            if (!date.after(currentDate)) {

                return "date must be after current date";
            }

        } catch (Exception e) {

            return "date must be after current date";
        }

        return "";
    }
}

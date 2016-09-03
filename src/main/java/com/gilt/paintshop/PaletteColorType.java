package com.gilt.paintshop;

/**
 * Palette Color Type defined by the requirement.
 */
public enum PaletteColorType {
    GLOSS("G"), MATTE("M");

    private String code;

    PaletteColorType(String code) {

        this.code = code;
    }

    /**
     * return corresponding PaletteColorType based on the receiving @code.
     *
     * @param code of PaletteColorType
     * @return PaletteColorType
     */
    public static PaletteColorType getType(String code) {

        for (PaletteColorType co : values()) {
            if (co.code.equalsIgnoreCase(code)) {
                return co;
            }
        }
        return null;
    }

    @Override
    public String toString() {

        return this.code;
    }
}
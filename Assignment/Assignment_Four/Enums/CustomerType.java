package Enums;

public enum CustomerType {
    ADMIN (true), USER(false);


    private boolean hasPrevilageForBankOperations;

      CustomerType(boolean hasPrevilageForBankOperations){
        this.hasPrevilageForBankOperations = hasPrevilageForBankOperations;
    }

    public boolean hasPrevilageForBankOperations() {
        return hasPrevilageForBankOperations;
    }
    
}

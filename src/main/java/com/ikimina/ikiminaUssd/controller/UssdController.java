package com.ikimina.ikiminaUssd.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ikimina.ikiminaUssd.dto.FineDTO;
import com.ikimina.ikiminaUssd.dto.LoanDTO;
import com.ikimina.ikiminaUssd.dto.SavingsDTO;
import com.ikimina.ikiminaUssd.model.Savings.SavingsType;
import com.ikimina.ikiminaUssd.model.User;
import com.ikimina.ikiminaUssd.service.FineService;
import com.ikimina.ikiminaUssd.service.LoanService;
import com.ikimina.ikiminaUssd.service.SavingsService;
import com.ikimina.ikiminaUssd.service.UserService;

@RestController
public class UssdController {
    private static final Map<String, String> userLanguages = new HashMap<>();
    private static final Map<String, String> translations = new HashMap<>();
    
    static {
        // English translations
        translations.put("welcome_unregistered_en", "Welcome to Ikimina USSD\n1. Register\n2. Change Language");
        translations.put("welcome_registered_en", "Welcome to Ikimina USSD\n1. Save Money\n2. Request Loan\n3. Check Balance\n4. View Reports\n5. Change Language");
        translations.put("admin_menu_en", "Admin Menu:\n1. View Today's Total Savings\n2. Add Fine\n3. Approve Loan\n4. Register New Admin");
        translations.put("savings_type_en", "Select savings type:\n1. Ingoboka\n2. Ubwizigame");
        translations.put("enter_amount_en", "Enter amount to save:");
        translations.put("success_save_en", "Successfully saved %s RWF");
        translations.put("enter_loan_amount_en", "Enter loan amount:");
        translations.put("loan_success_en", "Loan request submitted successfully");
        translations.put("balance_en", "Your balance:\nTotal Savings: %s RWF\nTotal Loans: %s RWF\nTotal Fines: %s RWF");
        translations.put("select_language_en", "Select Language:\n1. English\n2. Kinyarwanda");
        translations.put("report_period_en", "Select report period:\n1. Weekly\n2. Monthly");
        translations.put("report_en", "Report for %s:\nTotal Savings: %s RWF");
        translations.put("total_savings_en", "Today's total savings: %s RWF");
        translations.put("enter_user_phone_en", "Enter user phone number:");
        translations.put("enter_loan_id_en", "Enter loan ID:");
        translations.put("enter_fine_amount_en", "Enter fine amount:");
        translations.put("enter_fine_reason_en", "Enter fine reason:");
        translations.put("loan_approved_en", "Loan approved successfully");
        translations.put("fine_added_en", "Fine added successfully");
        translations.put("register_name_en", "Enter your full name:");
        translations.put("register_password_en", "Enter your password:");
        translations.put("register_success_en", "Registration successful! You can now use Ikimina services.");
        translations.put("register_error_en", "Registration failed: %s");
        translations.put("enter_new_admin_phone_en", "Enter new admin's phone number:");
        translations.put("enter_new_admin_name_en", "Enter new admin's full name:");
        translations.put("enter_new_admin_password_en", "Enter new admin's password:");
        translations.put("admin_registered_en", "New admin registered successfully!");
        translations.put("admin_register_error_en", "Failed to register admin: %s");
        
        // Kinyarwanda translations
        translations.put("welcome_unregistered_rw", "Murakaza neza kuri Ikimina USSD\n1. Kwiyandikisha\n2. Guhindura ururimi");
        translations.put("welcome_registered_rw", "Murakaza neza kuri Ikimina USSD\n1. Kuzigama\n2. Gusaba inguzanyo\n3. Kureba amafaranga yawe\n4. Kureba raporo\n5. Guhindura ururimi");
        translations.put("admin_menu_rw", "Menu y'umuyobozi:\n1. Kureba amafaranga yose yazigamwe uyu munsi\n2. Gutanga ihazabu\n3. Kwemeza inguzanyo\n4. Kwandika umuyobozi mushya");
        translations.put("savings_type_rw", "Hitamo uburyo bwo kuzigama:\n1. Ingoboka\n2. Ubwizigame");
        translations.put("enter_amount_rw", "Andika amafaranga ushaka kuzigama:");
        translations.put("success_save_rw", "Wazigamye %s RWF neza");
        translations.put("enter_loan_amount_rw", "Andika amafaranga y'inguzanyo:");
        translations.put("loan_success_rw", "Ubusabe bw'inguzanyo bwoherejwe neza");
        translations.put("balance_rw", "Amafaranga yawe:\nAyazigamwe yose: %s RWF\nInguzanyo: %s RWF\nIhazabu: %s RWF");
        translations.put("select_language_rw", "Hitamo ururimi:\n1. Icyongereza\n2. Ikinyarwanda");
        translations.put("report_period_rw", "Hitamo igihe cya raporo:\n1. Icyumweru\n2. Ukwezi");
        translations.put("report_rw", "Raporo ya %s:\nAmafaranga yose: %s RWF");
        translations.put("total_savings_rw", "Amafaranga yose yazigamwe uyu munsi: %s RWF");
        translations.put("enter_user_phone_rw", "Andika numero ya telefoni y'umunyamuryango:");
        translations.put("enter_loan_id_rw", "Andika nomero y'inguzanyo:");
        translations.put("enter_fine_amount_rw", "Andika amafaranga y'ihazabu:");
        translations.put("enter_fine_reason_rw", "Andika impamvu y'ihazabu:");
        translations.put("loan_approved_rw", "Inguzanyo yemejwe neza");
        translations.put("fine_added_rw", "Ihazabu yongeweho neza");
        translations.put("register_name_rw", "Andika amazina yawe yose:");
        translations.put("register_password_rw", "Andika ijambo ryibanga:");
        translations.put("register_success_rw", "Kwiyandikisha byagenze neza! Ubu ushobora gukoresha serivisi za Ikimina.");
        translations.put("register_error_rw", "Kwiyandikisha ntibyagenze neza: %s");
        translations.put("enter_new_admin_phone_rw", "Andika numero ya telefoni y'umuyobozi mushya:");
        translations.put("enter_new_admin_name_rw", "Andika amazina y'umuyobozi mushya:");
        translations.put("enter_new_admin_password_rw", "Andika ijambo ryibanga ry'umuyobozi mushya:");
        translations.put("admin_registered_rw", "Umuyobozi mushya yanditswe neza!");
        translations.put("admin_register_error_rw", "Kwandika umuyobozi mushya ntibyagenze neza: %s");
    }

    @Autowired
    private UserService userService;
    
    @Autowired
    private SavingsService savingsService;
    
    @Autowired
    private LoanService loanService;
    
    @Autowired
    private FineService fineService;

    private String getTranslation(String key, String language, Object... args) {
        String translationKey = key + "_" + language;
        String translation = translations.getOrDefault(translationKey, translations.get(key + "_en"));
        return String.format(translation, args);
    }

    @PostMapping("/ussd")
    public String handleUssd(
            @RequestParam String sessionId,
            @RequestParam String phoneNumber,
            @RequestParam String text) {
        
        String[] input = text.split("\\*");
        String language = userLanguages.getOrDefault(phoneNumber, "en");

        // Handle initial language selection for new users
        if (!userLanguages.containsKey(phoneNumber)) {
            if (text.isEmpty()) {
                return "CON " + translations.get("select_language_en");
            }
            if (input[0].equals("1")) {
                userLanguages.put(phoneNumber, "en");
            } else if (input[0].equals("2")) {
                userLanguages.put(phoneNumber, "rw");
            } else {
                return "END " + (language.equals("en") ? "Invalid selection" : "Wahisemo nabi");
            }
            return handleUssd(sessionId, phoneNumber, "");
        }

        // Check if user is registered
        boolean isRegistered = userService.findByPhoneNumber(phoneNumber).isPresent();
        boolean isAdmin = isRegistered && userService.findByPhoneNumber(phoneNumber).get().getRole().equals(User.Role.ADMIN);

        // Show appropriate menu based on registration status
        if (text.isEmpty()) {
            String welcome = getTranslation(isRegistered ? "welcome_registered" : "welcome_unregistered", language);
            if (isAdmin) {
                welcome += "\n6. " + (language.equals("en") ? "Admin Menu" : "Menu y'umuyobozi");
            }
            return "CON " + welcome;
        }

        // Handle unregistered user flow
        if (!isRegistered) {
            if (input[0].equals("1")) {
                return handleRegistration(input, phoneNumber, language);
            } else if (input[0].equals("2")) {
                return handleLanguageChange(input, phoneNumber, language);
            } else {
                return "END " + (language.equals("en") ? 
                    "Please register first by selecting option 1" : 
                    "Nyamuneka wiyandikishe mbere uhitamo 1");
            }
        }

        // Handle registered user flow
        switch (input[0]) {
            case "1":
                return handleSavings(input, phoneNumber, language);
            case "2":
                return handleLoans(input, phoneNumber, language);
            case "3":
                return handleBalance(phoneNumber, language);
            case "4":
                return handleReports(input, phoneNumber, language);
            case "5":
                return handleLanguageChange(input, phoneNumber, language);
            case "6":
                return isAdmin ? handleAdminMenu(input, phoneNumber, language) :
                    "END " + (language.equals("en") ? "Invalid option" : "Wahisemo nabi");
            default:
                return "END " + (language.equals("en") ? "Invalid option" : "Wahisemo nabi");
        }
    }

    private String handleLanguageChange(String[] input, String phoneNumber, String language) {
        if (input.length == 1) {
            return "CON " + getTranslation("select_language", language);
        } else if (input.length == 2) {
            if (input[1].equals("1")) {
                userLanguages.put(phoneNumber, "en");
            } else if (input[1].equals("2")) {
                userLanguages.put(phoneNumber, "rw");
            }
            return handleUssd(null, phoneNumber, "");
        }
        return "END " + (language.equals("en") ? "Invalid input" : "Ibyo winjije sibyo");
    }

    private String handleRegistration(String[] input, String phoneNumber, String language) {
        try {
            if (input.length == 1) {
                return "CON " + getTranslation("register_name", language);
            } else if (input.length == 2) {
                return "CON " + getTranslation("register_password", language);
            } else if (input.length == 3) {
                userService.registerUser(
                    phoneNumber,
                    input[1],
                    input[2],
                    User.Role.MEMBER
                );
                return "END " + getTranslation("register_success", language);
            }
            return "END " + (language.equals("en") ? "Invalid input" : "Ibyo winjije sibyo");
        } catch (RuntimeException e) {
            return "END " + String.format(getTranslation("register_error", language), e.getMessage());
        }
    }

    private String handleSavings(String[] input, String phoneNumber, String language) {
        if (input.length == 1) {
            return "CON " + getTranslation("savings_type", language);
        } else if (input.length == 2) {
            return "CON " + getTranslation("enter_amount", language);
        } else if (input.length == 3) {
            try {
                SavingsDTO savingsDTO = new SavingsDTO();
                savingsDTO.setPhoneNumber(phoneNumber);
                savingsDTO.setAmount(Double.valueOf(input[2]));
                savingsDTO.setType(input[1].equals("1") ? SavingsType.INGOBOKA : SavingsType.UBWIZIGAME);
                savingsDTO.setDate(LocalDate.now());
                
                savingsService.saveMoney(savingsDTO);
                return "END " + getTranslation("success_save", language, input[2]);
            } catch (NumberFormatException e) {
                return "END " + (language.equals("en") ? "Invalid amount format" : "Amafaranga winjije ntabwo ari ukuri");
            } catch (RuntimeException e) {
                return "END " + (language.equals("en") ? "Failed to save money: " : "Kuzigama byanze: ") + e.getMessage();
            }
        }
        return "END " + (language.equals("en") ? "Invalid input" : "Ibyo winjije sibyo");
    }

    private String handleLoans(String[] input, String phoneNumber, String language) {
        if (input.length == 1) {
            return "CON " + getTranslation("enter_loan_amount", language);
        } else if (input.length == 2) {
            try {
                LoanDTO loanDTO = new LoanDTO();
                loanDTO.setPhoneNumber(phoneNumber);
                loanDTO.setAmount(Double.valueOf(input[1]));
                
                loanService.requestLoan(loanDTO);
                return "END " + getTranslation("loan_success", language);
            } catch (NumberFormatException e) {
                return "END " + (language.equals("en") ? "Invalid amount format" : "Amafaranga winjije ntabwo ari ukuri");
            } catch (RuntimeException e) {
                return "END " + (language.equals("en") ? "Failed to request loan: " : "Kuzigama byanze: ") + e.getMessage();
            }
        }
        return "END " + (language.equals("en") ? "Invalid input" : "Ibyo winjije sibyo");
    }

    private String handleBalance(String phoneNumber, String language) {
        try {
            Double savings = savingsService.getTotalSavingsByUser(phoneNumber);
            Double loans = loanService.getTotalLoansByUser(phoneNumber);
            Double fines = fineService.getTotalFinesByUser(phoneNumber);
            
            return "END " + getTranslation("balance", language, savings, loans, fines);
        } catch (Exception e) {
            return "END " + (language.equals("en") ? "Failed to get balance: " : "Amafaranga byanze: ") + e.getMessage();
        }
    }

    private String handleReports(String[] input, String phoneNumber, String language) {
        if (input.length == 1) {
            return "CON " + getTranslation("report_period", language);
        } else if (input.length == 2) {
            try {
                LocalDate endDate = LocalDate.now();
                LocalDate startDate = input[1].equals("1") ? 
                    endDate.minusWeeks(1) : endDate.minusMonths(1);
                
                Double savings = savingsService.getUserSavingsByPeriod(phoneNumber, startDate, endDate)
                    .stream().mapToDouble(s -> s.getAmount()).sum();
                    
                return "END " + getTranslation("report", language, savings, input[1].equals("1") ? "last week" : "last month");
            } catch (Exception e) {
                return "END " + (language.equals("en") ? "Failed to get report: " : "Raporo byanze: ") + e.getMessage();
            }
        }
        return "END " + (language.equals("en") ? "Invalid input" : "Ibyo winjije sibyo");
    }

    private String handleAdminMenu(String[] input, String phoneNumber, String language) {
        if (!userService.findByPhoneNumber(phoneNumber).get().getRole().equals(User.Role.ADMIN)) {
            return "END " + (language.equals("en") ? "Access denied" : "Ubushobora kugana");
        }

        if (input.length == 1) {
            return "CON " + getTranslation("admin_menu", language);
        } else if (input.length == 2) {
            switch (input[1]) {
                case "1":
                    Double totalSavings = savingsService.getTotalSavingsByDate(LocalDate.now());
                    return "END " + getTranslation("total_savings", language, totalSavings);
                case "2":
                    return "CON " + getTranslation("enter_user_phone", language);
                case "3":
                    return "CON " + getTranslation("enter_loan_id", language);
                case "4":
                    return "CON " + getTranslation("enter_new_admin_phone", language);
                default:
                    return "END " + (language.equals("en") ? "Invalid option" : "Wahisemo nabi");
            }
        } else if (input[1].equals("4")) {
            // Handle new admin registration
            try {
                if (input.length == 3) {
                    return "CON " + getTranslation("enter_new_admin_name", language);
                } else if (input.length == 4) {
                    return "CON " + getTranslation("enter_new_admin_password", language);
                } else if (input.length == 5) {
                    userService.registerUser(
                        input[2], // new admin phone
                        input[3], // new admin name
                        input[4], // new admin password
                        User.Role.ADMIN
                    );
                    return "END " + getTranslation("admin_registered", language);
                }
            } catch (RuntimeException e) {
                return "END " + String.format(getTranslation("admin_register_error", language), e.getMessage());
            }
        } else if (input.length == 3) {
            if (input[1].equals("2")) {
                return "CON " + getTranslation("enter_fine_amount", language);
            } else if (input[1].equals("3")) {
                try {
                    loanService.approveLoan(phoneNumber, Long.valueOf(input[2]));
                    return "END " + getTranslation("loan_approved", language);
                } catch (Exception e) {
                    return "END " + (language.equals("en") ? "Failed to approve loan: " : "Kuzigama kubwoherejwe: ") + e.getMessage();
                }
            }
        } else if (input.length == 4 && input[1].equals("2")) {
            return "CON " + getTranslation("enter_fine_reason", language);
        } else if (input.length == 5 && input[1].equals("2")) {
            try {
                FineDTO fineDTO = new FineDTO();
                fineDTO.setPhoneNumber(input[2]);
                fineDTO.setAmount(Double.valueOf(input[3]));
                fineDTO.setReason(input[4]);
                
                fineService.createFine(fineDTO, phoneNumber);
                return "END " + getTranslation("fine_added", language);
            } catch (NumberFormatException e) {
                return "END " + (language.equals("en") ? "Invalid amount format" : "Amafaranga winjije ntabwo ari ukuri");
            } catch (RuntimeException e) {
                return "END " + (language.equals("en") ? "Failed to add fine: " : "Ihazabu kubwoherejwe: ") + e.getMessage();
            }
        }
        return "END " + (language.equals("en") ? "Invalid input" : "Ibyo winjije sibyo");
    }
} 
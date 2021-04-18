//Snippets from a fix for a Parser Differential bug (HPP)
@RestController
public class VerifyController {

    @Value("${paymenturl}")
    private String paymentUrl;

    @PostMapping("/")
    public String res(HttpServletRequest request) {
        try {
            ActionType action = ActionType.valueOf(request.getParameter("action").toUpperCase());
            Amount amount = new Amount(request.getParameter("amount"));
            if (ActionType.TRANSFER.equals(action)) {
                System.out.println("Verify Controller: Going to transfer $"+amount);
                RestTemplate restTemplate = new RestTemplate();
                String fakePaymentUrl = this.paymentUrl;  //Internal fake payment micro-service

                UriComponents uriComponents = UriComponentsBuilder.newInstance()
                        .scheme("http").host(fakePaymentUrl)
                        .queryParam("action", action.name().toLowerCase())
                        .queryParam("amount", amount.getAmount())
                        .build();
                ResponseEntity<String> response
                    = restTemplate.getForEntity(
                            uriComponents.toUriString(),
                            String.class);
                return response.getBody();
            } else if (ActionType.WITHDRAW.equals(action)) {
                return "Verify Controller: Sorry, you can only make transfer";
            } else {
                return "Verify Controller: You must specify action: transfer or withdraw";
            }
        } catch(RuntimeException ex) {
            throw new BadRequestException();
        }
    }

}

// Vulnerable part
//@RestController
//public class VerifyController {
//
//    @Value("${paymenturl}")
//    private String paymentUrl;
//
//    @PostMapping("/")
//    public String res(HttpServletRequest request) {
//        try {
//            String action = request.getParameter("action");
//            String amount = request.getParameter("amount");
//            if (action.equals("transfer")) {
//                System.out.println("Verify Controller: Going to transfer $"+amount);
//                RestTemplate restTemplate = new RestTemplate();
//                String fakePaymentUrl = this.paymentUrl;  //Internal fake payment micro-service
//                ResponseEntity<String> response 
//                    = restTemplate.getForEntity(
//                            fakePaymentUrl + "?action=" + action + "&amount=" + amount, 
//                            String.class);
//                return response.getBody();
//            } else if (action.equals("withdraw")) {
//                return "Verify Controller: Sorry, you can only make transfer";
//            } else {
//                return "Verify Controller: You must specify action: transfer or withdraw";
//            }
//        } catch(RuntimeException ex) {
//            throw new BadRequestException();
//        }
//    }
//
//}

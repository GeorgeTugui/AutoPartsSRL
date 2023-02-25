package ro.itschool.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ro.itschool.entity.MyUser;
import ro.itschool.entity.Order;
import ro.itschool.repository.*;
import ro.itschool.service.OrderService;
import ro.itschool.service.ShoppingCartService;
import ro.itschool.service.UserService;
import ro.itschool.util.Constants;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDComboBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDListBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDRadioButton;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.itschool.entity.Order;
import ro.itschool.service.OrderService;

import java.io.IOException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DetailingRepository detailingRepository;

    @Autowired
    private InteriorRepository interiorRepository;

    @Autowired
    private ExteriorRepository exteriorRepository;

    @GetMapping("/order")
    public String getAllOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping
    public String getOrderForPrincipal(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();
        MyUser userByUserName = userService.findUserByUserName(currentPrincipalName);
        model.addAttribute("orders", userByUserName.getOrders());

        return "orders";
    }

    @RequestMapping("/order/delete/{id}")
    public String deleteOrder(@PathVariable Integer id, Model model) {
        orderService.deleteOrderById(id);
        model.addAttribute("orders", orderRepository.findAll());

        return Constants.REDIRECT_TO_ORDERS;
    }

//    @GetMapping("/order/pdf/{id}")
//    public ResponseEntity<byte[]> exportOrderToPdf(@PathVariable Integer id, HttpServletResponse response) throws IOException {
//        Optional<Order> optionalOrder = orderService.findById(id);
//        if (optionalOrder.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        byte[] pdfContents = generatePdfForOrder(optionalOrder);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("order.pdf").build());
//        headers.setContentLength(pdfContents.length);
//
//        return new ResponseEntity<>(pdfContents, headers, HttpStatus.OK);
//    }

    @GetMapping("/order/pdf/{id}")
    public ResponseEntity<byte[]> exportOrderToPdf(@PathVariable Integer id) {
        Optional<Order> optionalOrder = orderService.findById(id);
        if (optionalOrder.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        byte[] pdfContents;
        try {
            pdfContents = generatePdfForOrder(optionalOrder);
        } catch (IOException e) {
            System.err.println("Error generating PDF for order " + id + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("order.pdf").build());
        headers.setContentLength(pdfContents.length);
        return ResponseEntity.ok().headers(headers).body(pdfContents);
    }


    private byte[] generatePdfForOrder(Optional<Order> optionalOrder) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        PDAcroForm form = new PDAcroForm(document);
        document.getDocumentCatalog().setAcroForm(form);

        // Set default resources for the form
        PDResources resources = new PDResources();
        form.setDefaultResources(resources);

        // Set default appearance string for form fields
        String defaultAppearanceString = "";
        form.setDefaultAppearance(defaultAppearanceString);

        // Add order information to the PDF
        PDTextField orderIdField = new PDTextField(form);
        orderIdField.setPartialName("Order ID");
        orderIdField.setValue(String.valueOf(optionalOrder.get().getId())); // assuming that the order has a getId() method
        form.getFields().add(orderIdField);

        // Add more fields as needed

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.save(outputStream);
        document.close();
        return outputStream.toByteArray();
    }


}


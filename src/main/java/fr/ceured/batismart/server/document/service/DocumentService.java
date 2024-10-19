package fr.ceured.batismart.server.document.service;

import fr.ceured.batismart.server.authentication.model.User;
import fr.ceured.batismart.server.authentication.service.UserService;
import fr.ceured.batismart.server.billing.model.Billing;
import fr.ceured.batismart.server.billing.model.LineQuantity;
import fr.ceured.batismart.server.client.model.Client;
import fr.ceured.batismart.server.client.service.ClientService;
import fr.ceured.batismart.server.designation.model.Designation;
import fr.ceured.batismart.server.designation.service.DesignationService;
import fr.ceured.batismart.server.document.exception.GenerateDocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DocumentService {

    private final ClientService clientService;
    private final DesignationService designationService;
    private final UserService userService;

    public String generateDocumentFromBilling(Billing billing) {

        Client client = clientService.getClientById(billing.getClientId());
        User user = userService.getById(billing.getUserId());

        Double subtotal = billing.getDesignationIds()
                .stream()
                .peek(d -> d.setDesignation(designationService.getById(d.getDesignationId())))
                .map(d ->  d.getQuantity() * d.getDesignation().getPrice())
                .reduce(0.0, Double::sum);

        Context context = new Context();
        context.setVariable("type", billing.getType().name());
        context.setVariable("date", billing.getDate());
        context.setVariable("numeroBilling", billing.getNumber());
        context.setVariable("numeroClient", client.getId());
        context.setVariable("address", user.getAddress() +" "+ user.getZipCode() + " " + user.getCity());
        context.setVariable("client", client.getFirstName() + " " + client.getLastName());
        context.setVariable("tel", user.getPhone());
        context.setVariable("email", user.getEmail());
        context.setVariable("website", user.getWebsite());
        context.setVariable("designation", buildDesignation(billing.getDesignationIds()));
        context.setVariable("subTotal", subtotal);
        context.setVariable("taxPercent", user.getTax());
        Double tax = user.getTax() != 0 ? subtotal * (user.getTax() / 100) : 0;
        context.setVariable("amountTax", tax);
        context.setVariable("discountPercent", billing.getDiscountPercent());
        double discount = billing.getDiscountPercent() != 0 ? -(subtotal * (billing.getDiscountPercent() / 100)) : 0;
        context.setVariable("amountDiscount", discount);
        context.setVariable("amountTotal", subtotal + tax - discount);
        context.setVariable("description", user.getFooter());

        String html = parseThymeleafTemplate(context);
        String b64File;
        try {
            b64File = generatePdfFromHtml(html);
        } catch (IOException e) {
            throw new GenerateDocumentException("Error while generate document");
        }

        return b64File;
    }

    private String buildDesignation(List<LineQuantity> designationIds) {
        StringBuilder designation = new StringBuilder();
        designation.append("<tr style=\"text-align: left;\"><th>Désignation</th><th>Quantité</th><th>Prix Unitaire</th><th>Total</th></tr>");

        designationIds.forEach(lineQuantity -> {
            Designation design = designationService.getById(lineQuantity.getDesignationId());

            switch (design.getTypeDesignation()) {
                case CATEGORY -> designation.append("<tr>").append("<td colspan=\"4\" style=\"background-color\">").append(design.getName()).append("</td>");
                case LINE -> designation.append("<td>").append(design.getName()).append("</td>")
                        .append("<td>").append(lineQuantity.getQuantity()).append("</td>")
                        .append("<td>").append(design.getPrice()).append("</td>")
                        .append("<td>").append(lineQuantity.getQuantity() * design.getPrice()).append("</td>");
            }
        });

        return designation.toString();
    }

    private String parseThymeleafTemplate(Context context) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");

        TemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        return templateEngine.process("test", context);
    }

    public String generatePdfFromHtml(String html) throws IOException {
        String outputFolder = System.getProperty("user.home") + File.separator + "generated.pdf";
        OutputStream outputStream = new FileOutputStream(outputFolder);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);

        outputStream.close();

        File file = new File(outputFolder);
        byte[] encoded = Base64.getEncoder().encode(Files.readAllBytes(file.toPath()));
        return new String(encoded, StandardCharsets.UTF_8);
    }
}

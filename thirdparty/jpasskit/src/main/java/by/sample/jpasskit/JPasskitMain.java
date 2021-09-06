/* Copyright © 2021 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
  CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.
 */
package by.sample.jpasskit;

import de.brendamour.jpasskit.*;
import de.brendamour.jpasskit.enums.*;
import de.brendamour.jpasskit.passes.*;
import de.brendamour.jpasskit.signing.*;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;

/**
 * @author sshchahratsou
 */
public class JPasskitMain {
    //PREC-AU specific resources
    private static final String BASE_PATH = "AutoPolicy.pass/";

    //Core resources (META-INF to restrict access from HTTP)
    private static final String CERTIFICATE = "META-INF/AppleWWDRCA.cer";
    private static final String KEYSTORE = "META-INF/Keystore.p12";

    //should be protected by e.g. 'jasypt' and store in properties file
    private static final String KEYSTORE_PASS = "secret";

    public static void main(String[] args) throws Exception {
        PKPassTemplateInMemory template = new PKPassTemplateInMemory();

        append(template, PKPassTemplateInMemory.PK_ICON_RETINA);
        append(template, PKPassTemplateInMemory.PK_ICON);
        append(template, PKPassTemplateInMemory.PK_LOGO_RETINA);
        append(template, PKPassTemplateInMemory.PK_LOGO);
        append(template, PKPassTemplateInMemory.PK_THUMBNAIL_RETINA);
        append(template, PKPassTemplateInMemory.PK_THUMBNAIL);

        InputStream keyStoreStream = getResourceStream(KEYSTORE);
        InputStream appleWWDRCAStream = getResourceStream(CERTIFICATE);
        PKSigningInformation pkSigningInformation = new PKSigningInformationUtil().loadSigningInformationFromPKCS12AndIntermediateCertificate(keyStoreStream, KEYSTORE_PASS, appleWWDRCAStream);
        PKFileBasedSigningUtil pkSigningUtil = new PKFileBasedSigningUtil();
        PKPass pass = generate();
        byte[] signedAndZippedPkPassArchive = pkSigningUtil.createSignedAndZippedPkPassArchive(pass, template, pkSigningInformation);

        Path pkPath = Files.createTempFile("AutoPolicy", ".pkpass");
        System.out.println("Generating " + pkPath.toString());
        Files.write(pkPath, signedAndZippedPkPassArchive);
    }

    private static void append(PKPassTemplateInMemory tmpl, String resourceName) throws Exception {
        InputStream resourceStream = getResourceStream(BASE_PATH + resourceName);
        tmpl.addFile(resourceName, resourceStream);
    }

    private static InputStream getResourceStream(String resource) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
    }

    private static final String POLICY_NUMBER = "P1234567890";
    private static final Date POLICY_EFFECTIVE = Date.from(
            LocalDateTime.of(2020, 12, 12, 0, 0)
                    .atZone(ZoneId.systemDefault())
                    .toInstant());
    private static final Date POLICY_EXPIRATION = Date.from(
            LocalDateTime.of(2021, 12, 12, 0, 0)
                    .atZone(ZoneId.systemDefault())
                    .toInstant());
    private static final String DESCRIPTION = "EIS Auto Policy";
    private static final String LOGO = "EIS Auto Policy Card";
    //{{vehicleMake}} {{vehicleModel}}, {{vehicleYear}}
    private static final String VEHICLE = "Ford Mondeo 2010";
    //{{insuredFirstName}} {{insuredLastName}}
    private static final String INSURED = "Siarhei Shchahratsou";
    private static final String VIN = "HJGSFUSR48KJ847";
    //{{vehicleAddressLine1}}, {{vehicleCity}} {{vehicleState}} {{vehiclePostalCode}}
    private static final String GARAGE = "123, Addison IL 60101";
    //{{glxAppUrl}}/flow?_flowId=policy-detail-flow&policyNumber={{policyNumber}}#noback
    private static final String BARCODE = "https://dev5-glx-app01.eisgroup.com/glx-app/flow?_flowId=policy-detail-flow&policyNumber=P1234567890#noback";

    //static impl for PREC-AU
    private static PKPass generate() {
        PKPass pass = new PKPass();
        pass.setFormatVersion(1);
        pass.setPassTypeIdentifier("pass.com.exigeninsurance.SelfServices");
        pass.setSerialNumber(POLICY_NUMBER);
        pass.setTeamIdentifier("5FD8V7S4S6");
        pass.setOrganizationName("EIS");
        pass.setDescription(DESCRIPTION);
        pass.setLogoText(LOGO);
        pass.setExpirationDate(POLICY_EXPIRATION);
        pass.setForegroundColor("rgb(255, 255, 255)");
        pass.setBackgroundColor("rgb(223, 134, 48)");

        PKGenericPass genericPass = new PKGenericPass();
        pass.setGeneric(genericPass);

        List<PKField> primaryFields = new ArrayList<>();
        genericPass.setPrimaryFields(primaryFields);
        primaryFields.add(new PKField("policyNumber", "Policy #", POLICY_NUMBER));

        List<PKField> secondaryFields = new ArrayList<>();
        genericPass.setSecondaryFields(secondaryFields);
        secondaryFields.add(new PKField("vehicleName", "Vehicle", VEHICLE));
        secondaryFields.add(new PKField("insured", "Insured", INSURED));

        List<PKField> auxFields = new ArrayList<>();
        genericPass.setAuxiliaryFields(auxFields);
        auxFields.add(new PKField("vehicleVin", "VIN", VIN));
        PKField effDateField = new PKField("effectiveDate", "Effective", POLICY_EFFECTIVE);
        effDateField.setDateStyle(PKDateStyle.PKDateStyleShort);
        auxFields.add(effDateField);
        PKField expDateField = new PKField("expirationDate", "to", POLICY_EXPIRATION);
        expDateField.setDateStyle(PKDateStyle.PKDateStyleShort);
        auxFields.add(expDateField);

        List<PKField> backFields = new ArrayList<>();
        genericPass.setBackFields(backFields);
        backFields.add(new PKField("garagingAddress", "Garaging address", GARAGE));

        List<PKBarcode> barcodes = new ArrayList<>();
        pass.setBarcodes(barcodes);
        PKBarcode barcode = new PKBarcode();
        barcode.setFormat(PKBarcodeFormat.PKBarcodeFormatQR);
        barcode.setMessageEncoding(StandardCharsets.ISO_8859_1);
        barcode.setMessage(BARCODE);

        return pass;
    }
}

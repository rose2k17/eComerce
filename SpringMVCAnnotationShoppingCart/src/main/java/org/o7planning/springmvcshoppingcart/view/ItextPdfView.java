package org.o7planning.springmvcshoppingcart.view;

import java.text.DateFormat;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.o7planning.springmvcshoppingcart.model.CartInfo;
import org.o7planning.springmvcshoppingcart.model.CartLineInfo;
import org.o7planning.springmvcshoppingcart.util.Utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ItextPdfView extends AbstractITextPdfView {

    private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance(DateFormat.SHORT);


    @Override
    protected void buildPdfDocument(Map<String, Object> model,
                                    Document document, PdfWriter writer, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {

        @SuppressWarnings("unchecked")
        //List<Course> courses = (List<Course>) model.get("courses");
        CartInfo cartInfo = Utils.getCartInSession(request);

        PdfPTable table = new PdfPTable(4);
        table.setWidths(new int[]{25, 35, 15, 25});

        table.addCell("Nombre");
        table.addCell("Email");
        table.addCell("Telefono");
        table.addCell("Direccion");

        /*for (Course course : courses){
            table.addCell(String.valueOf(course.getId()));
            table.addCell(course.getName());
            table.addCell(DATE_FORMAT.format(course.getDate()));
        }*/
        
        table.addCell(cartInfo.getCustomerInfo().getName());
        table.addCell(cartInfo.getCustomerInfo().getEmail());
        table.addCell(cartInfo.getCustomerInfo().getPhone());
        table.addCell(cartInfo.getCustomerInfo().getAddress());
        
        table.setSpacingAfter(6);

        document.add(table);
        
        PdfPTable prods = new PdfPTable(4);
        prods.setWidths(new int[]{15, 35, 20, 25});
        prods.addCell("Code");
        prods.addCell("Producto");
        prods.addCell("Unidades");
        prods.addCell("Precio");

        for (CartLineInfo cartInfos : cartInfo.getCartLines()){
        	prods.addCell(cartInfos.getProductInfo().getCode());
        	prods.addCell(cartInfos.getProductInfo().getName());
        	prods.addCell(String.valueOf(cartInfos.getQuantity()));
        	prods.addCell(String.valueOf(cartInfos.getAmount()));
        }

        document.add(prods);
    }

}

package document;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class XmlModify {
    public static void main(String[] args) {
        Document document = null;
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(new File("C:\\Users\\junlie\\Desktop\\长安\\word\\document.xml"));

            Element root = document.getRootElement();
            Element bElement = root.elements().get(0);
            Element pElement = bElement.elements().get(0);
            Element rElement = pElement.elements().get(1);
            Element rPrElement = rElement.elements().get(0);

            Element sbElement = DocumentHelper.createElement("w:b");
            //rPrElement.elements().add(4,sbElement);

            Element scElement = rPrElement.elements().get(1);
            Attribute attribute = scElement.attribute("val");
            //attribute.setValue("72");


            Element iElement = DocumentHelper.createElement("w:i");
            //rPrElement.elements().add(5,iElement);

            Element colorElement = DocumentHelper.createElement("w:color").addAttribute("w:val", "FFFF00");
            //rPrElement.elements().add(5,colorElement);


            System.out.println(colorElement);


            //写回文件
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            XMLWriter writer = new XMLWriter(new FileWriter("C:\\Users\\junlie\\Desktop\\长安\\word\\document.xml"), format);
            writer.write(document);
            writer.close();

        } catch (DocumentException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package btags.parser;

import btags.body.Purchase;
import btags.body.Shop;
import datas.Body;
import datas.RequestMessage;
import datas.ResponseMessage;
import exceptions.BadRequestException;
import parser.BtagParser;
import tokens.GeneralToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShopParser implements BtagParser {
    public static final String BTAG_NAME = "shop";
    public String getBtagName() {
        return BTAG_NAME;
    }

    public Body parseToBody(BufferedReader reader) throws IOException, BadRequestException {
        int purchaseCount = 0;
        List<String> purchases = new ArrayList<String>();
        String line;
        try {
            while((line = reader.readLine()) != null){
                if (GeneralToken.END_SEP.equalsIgnoreCase(line))
                    break;
                if (GeneralToken.BODY_SEP.equalsIgnoreCase(line))
                    continue;
                String[] tokens = line.split(GeneralToken.FIELD_SEP);
                if (Shop.PURCHASE_COUNT.equalsIgnoreCase(tokens[0]))
                    purchaseCount = (tokens.length>1)?Integer.parseInt(tokens[1]):0;
                else if (Shop.PURCHASE.equalsIgnoreCase(tokens[0]))
                    if (tokens.length>1)
                        purchases.add(tokens[1]);
            }
        } catch (NumberFormatException e) {
            throw new BadRequestException();
        }
        return new Shop(purchaseCount, purchases);
    }

    public String parseToString(RequestMessage request) {
        String msg = "";
        msg += Shop.PURCHASE_COUNT + GeneralToken.FIELD_SEP + request.getIntegerValue(Shop.PURCHASE_COUNT) + "\n";
        for (String purchase : request.getStringList(Shop.PURCHASE))
            msg += Shop.PURCHASE + GeneralToken.FIELD_SEP + purchase + "\n";
        return msg;
    }

    public String parseToString(ResponseMessage response) {
        return null;
    }

    public Purchase parsePurchase(String purchase){
        String[] token = purchase.split(" ");
        String name = token[0];
        int amt = Integer.parseInt(token[1]);
        double price = Double.parseDouble(token[2]);

        return new Purchase(name, amt, price);
    }
}

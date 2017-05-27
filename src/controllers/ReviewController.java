
package controllers;

//import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import server.SqlHandler;
import unsw.curation.api.domain.ExtractNamedEntity;
import unsw.curation.api.domain.ExtractPosTag;
import unsw.curation.api.extractnamedentity.ExtractEntitySentence;
import unsw.curation.api.extractpostag.ExtractPosTagData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kriss on 25-May-17.
 */
public class ReviewController {

    public ReviewController(){

    }

    public void postReview(HttpServletRequest request, HttpServletResponse response){
        int bookid = Integer.parseInt(request.getParameter("bookid"));
        String review = request.getParameter("review");
        SqlHandler sqlHandler = new SqlHandler();
        sqlHandler.connect();
        sqlHandler.addReview(bookid, review);
        sqlHandler.closeConnection();
    }

    public void extractReview(HttpServletRequest request, HttpServletResponse response){
        ArrayList<ExtractPosTag> posArray = new ArrayList<>();
        String review = request.getParameter("review");
        try {

            ExtractEntitySentence sentenceEntity=new ExtractEntitySentence();
            List<ExtractNamedEntity> lstSentenceEntities=sentenceEntity.ExtractNamedEntitySentence(review);
            for(ExtractNamedEntity sEntity:lstSentenceEntities)
            {
                System.out.println(sEntity.word+" "+sEntity.ner);
            }



            ExtractPosTagData posSentance=new ExtractPosTagData();
            List<ExtractPosTag> lstVerb=posSentance.ExtractVerb(review);
            posArray.addAll(lstVerb);

            List<ExtractPosTag> lstAdjective=posSentance.ExtractAdjective(review);
            posArray.addAll(lstAdjective);

            List<ExtractPosTag> lstAdverb=posSentance.ExtractAdverb(review);
            posArray.addAll(lstAdverb);
            JSONArray jsonArray = new JSONArray();
            JSONObject mainObject = new JSONObject();
            for(ExtractPosTag e : posArray){
                System.out.println("teller");
                JSONObject json = new JSONObject();
                json.put("word", e.getWordPart());
                json.put("tag", e.getTag());
                jsonArray.put(json);
            }
            mainObject.put("extracted", jsonArray);
            System.out.println(jsonArray.toString());
            response.getWriter().write(mainObject.toString());
//            request.setAttribute("posArray", posArray);
//            request.getRequestDispatcher("header.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


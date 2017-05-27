
package controllers;

//import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import server.SqlHandler;
import unsw.curation.api.classify.api.domain.ExtractSynonym;
import unsw.curation.api.classify.api.extractsynonym.WordNetFile;
import unsw.curation.api.classify.api.tokenization.ExtractionKeywordImpl;
import unsw.curation.api.domain.ExtractNamedEntity;
import unsw.curation.api.domain.ExtractPosTag;
import unsw.curation.api.extractnamedentity.ExtractEntitySentence;
import unsw.curation.api.extractpostag.ExtractPosTagData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
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
        JSONArray jsonArray = new JSONArray();
        JSONObject mainObject = new JSONObject();
        try {
            ExtractionKeywordImpl keywordIns=new ExtractionKeywordImpl();
            String sentences= keywordIns.ExtractSentenceKeyword(review, new File("C:\\Users\\kriss\\Documents\\Skole\\Australia\\workspace\\WebApp4\\englishStopwords.txt"));
            System.out.println("KEYWORDS!!!!");
            System.out.println(sentences);
            for(String sentence: sentences.split(",")){
                JSONObject json = new JSONObject();
                json.put("tag","keyword");
                json.put("word",sentence);
                jsonArray.put(json);
            }
            mainObject.put("keywords", jsonArray);
            jsonArray=new JSONArray();
            ExtractEntitySentence sentenceEntity=new ExtractEntitySentence();
            List<ExtractNamedEntity> lstSentenceEntities=sentenceEntity.ExtractNamedEntitySentence(review);
            for(ExtractNamedEntity sEntity:lstSentenceEntities)
            {
                System.out.println(sEntity.word+" "+sEntity.ner);
                JSONObject json = new JSONObject();
                json.put("tag", sEntity.ner);
                json.put("word", sEntity.word);
                jsonArray.put(json);
            }
            mainObject.put("named", jsonArray);

            jsonArray = new JSONArray();
            WordNetFile extractWordnet=new WordNetFile();
            List<ExtractSynonym>lstSynonyms=extractWordnet
                    .ExtractSynonymSentence(review);
            for(ExtractSynonym synonym:lstSynonyms)
            {
                System.out.println(synonym.getWord()+": "+synonym.getSynset());
                if(synonym.getSynset().length()>1) {
                    JSONObject json = new JSONObject();
                    json.put("tag", synonym.getSynset());
                    json.put("word", synonym.getWord());
                    jsonArray.put(json);
                }
            }
            mainObject.put("synonyms", jsonArray);
            jsonArray = new JSONArray();


            ExtractPosTagData posSentance=new ExtractPosTagData();
            List<ExtractPosTag> lstVerb=posSentance.ExtractVerb(review);
            posArray.addAll(lstVerb);

            List<ExtractPosTag> lstAdjective=posSentance.ExtractAdjective(review);
            posArray.addAll(lstAdjective);

            List<ExtractPosTag> lstAdverb=posSentance.ExtractAdverb(review);
            posArray.addAll(lstAdverb);
            for(ExtractPosTag e : posArray){
                JSONObject json = new JSONObject();
                json.put("word", e.getWordPart());
                json.put("tag", e.getTag());
                jsonArray.put(json);
            }
            mainObject.put("pos", jsonArray);
            System.out.println(jsonArray.toString());
            response.getWriter().write(mainObject.toString());
//            request.setAttribute("posArray", posArray);
//            request.getRequestDispatcher("header.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


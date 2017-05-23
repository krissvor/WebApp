package unsw.curation.api.extractsimilarity;

import org.apache.lucene.store.LockObtainFailedException;
import unsw.curation.api.cosinetext.CosineSimilarity;
import unsw.curation.api.cosinetext.DocVector;
import unsw.curation.api.cosinetext.Index;
import unsw.curation.api.cosinetext.VectorGenerator;
import unsw.curation.api.domain.ExtractTextCosineSimilarity;
import unsw.curation.api.domain.abstraction.ITextCosineSimilarity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class ExtractTextCosineSimilarityImpl implements ITextCosineSimilarity{

	 List<ExtractTextCosineSimilarity> lstCosineSimilarity=new ArrayList<>();
	    public List<ExtractTextCosineSimilarity> Cosine_Document_DocumentS(String QueryFilePath, String DataDirectoryPath) throws LockObtainFailedException, IOException
	    {
	        File f=new File(QueryFilePath);
	        String QueryFileName=f.getName();
	        Index indexing=new Index(DataDirectoryPath);
	        int docVectorID=-1;
	        indexing.index();
	        VectorGenerator vectorGenerator = new VectorGenerator();
	        vectorGenerator.GetAllTerms();       
	        DocVector[] docVector = vectorGenerator.GetDocumentVectors();
	        List<VectorGenerator> lstVal=vectorGenerator.getLstData();
	        for(VectorGenerator item:lstVal)
	        {
	            if(item.DocName.toLowerCase().trim().equals(QueryFileName.toLowerCase().trim()))
	            {
	                docVectorID=item.DocId;
	            }
	        }
	        if(docVectorID!=-1)
	        {
	           for(int i = 0; i <vectorGenerator.getLstData().size(); i++)
	           {
	               double cosineSimilarity = CosineSimilarity.
	                       CosineSimilarity(docVector[docVectorID], docVector[i]);
	               String myDocName=vectorGenerator.getLstData().get(docVectorID).DocName;
//	               System.out.println("Similarity: "+vectorGenerator.
//	                       getLstData().get(docVectorID).DocName +" "+ 
//	                       vectorGenerator.getLstData().get(i).DocName+""
//	                       + "= " + cosineSimilarity);
	               lstCosineSimilarity.add(new ExtractTextCosineSimilarity(myDocName,
	               vectorGenerator.getLstData().get(i).DocName,
	               cosineSimilarity));
	           }
	        }
	        else
	        {
	            System.err.println("Incorrect File Name.....");
	        }
	        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
	        Collections.sort(lstCosineSimilarity, new MyCosineComp());
	        lstCosineSimilarity=lstCosineSimilarity.stream()
	                .limit(10)
	                .collect(Collectors.toList());
	                
	        return lstCosineSimilarity;
	    }

    /*List<ExtractTextCosineSimilarity> lstCosineSimilaritySen=new ArrayList<>();
    public List<ExtractTextCosineSimilarity> Cosine_Sentence_Document(String Query, String FileName) throws LockObtainFailedException, IOException
    {
        IndexSentence indexing=new IndexSentence(FileName,Query);
        int docVectorID=-1;
        indexing.index();
        VectorGeneratorSentence vectorGenerator2 = new VectorGeneratorSentence();
        vectorGenerator2.GetAllTerms();       
        DocVectorSentence[] docVector2 = vectorGenerator2.GetDocumentVectors();
        List<VectorGeneratorSentence> lstVal=vectorGenerator2.getLstData();
        for(VectorGeneratorSentence item:lstVal)
        {
            if(item.DocName.toLowerCase().trim().equals(Query.toLowerCase().trim()))
            {
                docVectorID=item.DocId;
            }
        }
        if(docVectorID!=-1)
        {
           for(int i = 0; i <vectorGenerator2.getLstData().size(); i++)
           {
               double MycosineSimilarity = CosineSimilaritySentence.
               CosineSimilarity(docVector2[docVectorID], docVector2[i]);
               if(MycosineSimilarity>0.0)
               {
                   String myDocName=vectorGenerator2.getLstData().get(docVectorID).DocName;
                   lstCosineSimilaritySen.add(new ExtractTextCosineSimilarity(myDocName,
                   vectorGenerator2.getLstData().get(i).DocName,
                   MycosineSimilarity));
               }
           }
        }
        else
        {
            System.err.println("Incorrect File Name.....");
        }
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        Collections.sort(lstCosineSimilaritySen, new MyCosineComp());
        lstCosineSimilaritySen=lstCosineSimilaritySen.stream()
                .limit(10)
                .collect(Collectors.toList());
                
        return lstCosineSimilaritySen;
    }*/
    
    
    public class MyCosineComp implements Comparator<ExtractTextCosineSimilarity>
    {
        @Override
        public int compare(ExtractTextCosineSimilarity o1, ExtractTextCosineSimilarity o2) {
            if(o1.Similarity<o2.Similarity)
                return 1;
            else
                return -1;
        }
    }	

}

package unsw.curation.api.classify.api.domain.abstraction;

import unsw.curation.api.classify.api.domain.ExtractTextSimilarity;

import java.io.IOException;
import java.util.List;


public interface ITextJaccardSimilarity {

	double Jaccard_Word_Word(String word1, String word2);
	List<ExtractTextSimilarity> Jaccard_Word_Document(String word, String filePath) throws IOException;
	double Jaccard_Document_Document(String file1, String file2) throws IOException;
	//List<ExtractTextSimilarity> Jaccard_Document_DocumentS(File filePath, String directoryPath) throws IOException;
	
}

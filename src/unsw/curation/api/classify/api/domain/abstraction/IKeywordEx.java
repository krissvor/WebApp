package unsw.curation.api.classify.api.domain.abstraction;
import unsw.curation.api.classify.api.domain.ExtractionKeyword;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface IKeywordEx {

	String ExtractTweetKeyword(String inputTweet, File stopWordList) throws Exception;
	List<ExtractionKeyword> ExtractTweetKeywordFromFile(File fileName, File stopWordList) throws FileNotFoundException, IOException;
	String ExtractSentenceKeyword(String inputSentence, File stopWordList) throws Exception;
	//String ExtractSentenceKeyPhrase(String inputSentence,File stopWordList) throws Exception;
	String ExtractFileKeyword(File fileName, File stopWordList) throws FileNotFoundException, IOException;
	/*ExtractionKeyword ExtractSentenceKeywords(String inputSentence) throws Exception;
	ExtractionKeyword ExtractFileKeywords(String inputFilePath) throws Exception;*/
}

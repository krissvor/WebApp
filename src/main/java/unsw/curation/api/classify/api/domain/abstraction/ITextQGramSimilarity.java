package unsw.curation.api.classify.api.domain.abstraction;

public interface ITextQGramSimilarity {

	double ComputeQGramSimilarity(String word1, String word2);
}

package prepare.create;

import java.util.List;
import java.util.Map;

import com.eg.fireman.bean.Question;
import com.eg.fireman.jdbc.QuestionJdbc;
import com.eg.fireman.util.FiremanUtil;

public class InsertMultiple {
	public static void main(String[] args) {
		List<Question> questionList = FiremanUtil.getQuestionList();
		for (Question question : questionList) {
			Map<Integer, String> answerMap = FiremanUtil.getAnswerMap();
			String answer = answerMap.get(question.getQuestionId());
			QuestionJdbc.insertQuestion(question.getQuestionId(), question.getQuestion(), answer, question.getOptionA(),
					question.getOptionB(), question.getOptionC(), question.getOptionD());
		}
	}
}

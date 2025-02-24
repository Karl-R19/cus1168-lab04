package academy.javapro;

class ExpressionParser {
	private final String input;
	private int position;

	public ExpressionParser(String input) {
		this.input = input;
		this.position = 0;
	}

	// expr → expr + term
	public double parseExpression() {

		double result = parseTerm(); // Parse the first term

		while (position < input.length() && input.charAt(position) == '+') {
			position++; // Move past '+'
			result += parseTerm(); 
		}
		return result;
	}

	// term → term * factor
	private double parseTerm() {
		double result = parseFactor(); // Get the first factor

		while (position < input.length() && input.charAt(position) == '*') {
			position++; // Move past '*'
			result *= parseFactor(); // Multiply with the next factor
		}

		return result;
	}

	// factor → ( expr )
	private double parseFactor() {
		if (position < input.length() && input.charAt(position) == '(') {
			position++; // Skip '('
			double result = parseExpression(); // Recursively evaluate inner expression
			if (position < input.length() && input.charAt(position) == ')') {
				position++; // Skip ')'
			} else {
				throw new UnsupportedOperationException("Implement parseFactor");
			}
			return result;
		} else {
			return parseNumber(); // Parse numeric value
		}
	}

	// Parse a numeric value
	private double parseNumber() {
		StringBuilder numStr = new StringBuilder();

		while (position < input.length()
				&& (Character.isDigit(input.charAt(position)) || input.charAt(position) == '.')) {
			numStr.append(input.charAt(position));
			position++;
		}

		if (numStr.length() == 0) {
			throw new UnsupportedOperationException("Implement parseNumber");
		}

		return Double.parseDouble(numStr.toString());
	}

	public static void main(String[] args) {
		// Test cases
		String[] testCases = { "2 + 3 * (4 + 5)", // Complex expression with parentheses
				"2 + 3 * 4", // Basic arithmetic with precedence
				"(2 + 3) * 4", // Parentheses changing precedence
				"2 * (3 + 4) * (5 + 6)", // Multiple parentheses
				"1.5 + 2.5 * 3" // Decimal numbers
		};

		// Process each test case
		for (String expression : testCases) {
			System.out.println("\nTest Case: " + expression);
			try {
				ExpressionParser parser = new ExpressionParser(expression.replaceAll("\\s+", "")); // Remove spaces
				double result = parser.parseExpression();
				System.out.println("Result: " + result);
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}
}

package InfixToPostfix;

import java.util.ArrayList;
import java.util.Stack;

public class InfixToPostfix {

	public static void main(String[] args) {
		String expression = "11+1+2*1+3*(1+1)";
		Stack<String> tmp = new InfixToPostfix().infixToPostfix(expression);
		ArrayList<String> res = new ArrayList<>(tmp);
		for (String item : res) {
			System.out.print(item + " ");
		}
		System.out.println("\nResult: " + InfixToPostfix.calculatePostfix(res));

	}

	private Stack<String> infixToPostfix(String str) {
		Stack<String> res = new Stack<>();
		Stack<String> stack = new Stack<>();

		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			// Nếu là toán hạng (số) thì tiếp tục đọc các ký tự tiếp theo cho đến không phải
			// là số.
			// trong khi lặp lại việc trên, nối các ký tự số theo thứ tự để được số có nhiều
			// chữ số
			// Sau đó, cho số đọc được vào mảng res.
			if (c >= '1' && c <= '9') {
				String number = Character.toString(c);
				int next_index = i + 1;
				while (next_index < str.length()) {
					char next_char = str.charAt(next_index);
					if (next_char >= '1' && next_char <= '9') {
						number += next_char;
						next_index++;
						i++;
					} else {
						break;
					}
				}
				res.push(number);
				continue;
			} else if (c == '(') {
				// Nếu là dấu mở ngoặc “(“: cho vào stack
				stack.push(Character.toString(c)); // convert character to string
			} else if (c == ')') {
				// Nếu là dấu đóng ngoặc “)”:
				// lấy các toán tử trong stack ra và cho vào res
				// cho đến khi gặp dấu mở ngoặc “(“.
				// (Dấu mở ngoặc cũng phải được đưa ra khỏi stack)
				while (!stack.peek().equals("(")) {
					res.push(stack.pop());
				}
				stack.pop();
			} else if (c == '+' || c == '-' || c == '*' || c == '/') {
				// Chừng nào ở đỉnh stack là toán tử và toán tử đó
				// có độ ưu tiên lớn hơn hoặc bằng toán tử hiện tại
				// thì lấy toán tử đó ra khỏi stack và cho ra output.
				// Đưa toán tử hiện tại vào stack
				while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek().charAt(0))) {
					res.push(stack.pop());
				}
				stack.push(Character.toString(c));
			}
		}
		// Sau khi duyệt hết biểu thức infix,
		// nếu trong stack còn phần tử thì lấy các token trong đó ra
		// và cho lần lượt vào output.
		while (!stack.isEmpty()) {
			res.push(stack.pop());
		}
		return res;
	}

	// Hàm trả về độ ưu tiên của các toán tử
	private int precedence(char operator) {
		switch (operator) {
		case '*':
		case '/':
			return 2;
		case '+':
		case '-':
			return 1;
		default:
			return 0;
		}
	}

	// Tính toán biểu thức ở dạng hậu tố
	private static Float calculatePostfix(ArrayList<String> expression) {
		Stack<Float> numberStack = new Stack<Float>();
		Float cal = new Float(0);
		for (String item : expression) {
			switch (item) {
			case "+":
				cal = numberStack.pop() + numberStack.pop();
				numberStack.push(cal);
				break;
			case "-":
				cal = -numberStack.pop() + numberStack.pop();
				numberStack.push(cal);
				break;
			case "*":
				cal = numberStack.pop() * numberStack.pop();
				numberStack.push(cal);
				break;
			case "/":
				cal = 1 / numberStack.pop() / numberStack.pop();
				numberStack.push(cal);
				break;
			default:
				numberStack.push(Float.parseFloat(item));
				break;
			}
		}
		return numberStack.pop();
	}
}

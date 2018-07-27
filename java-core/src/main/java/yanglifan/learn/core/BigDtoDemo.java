package yanglifan.learn.core;

public class BigDtoDemo {

	class OrderController {
		public void createAndPayOrder(CreateOrderRequest request) {
			validateRequest(request);
			queryUser(request);
			checkCoupon(request);
			queryOrder(request);
			createOrder(request);
			saveOrder(request);
		}

		private void saveOrder(CreateOrderRequest request) {
		}

		private void createOrder(CreateOrderRequest request) {

		}

		private void queryOrder(CreateOrderRequest request) {
		}

		private void checkCoupon(CreateOrderRequest request) {

		}

		private void queryUser(CreateOrderRequest request) {
		}

		private void validateRequest(CreateOrderRequest request) {

		}
	}

	class CreateOrderRequest {
		// properties
	}
}

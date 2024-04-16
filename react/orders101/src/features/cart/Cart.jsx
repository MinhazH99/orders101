function Cart() {
  return (
    <div class="cart">
      <h2 class="card-title">Your Cart</h2>

      <div class="cart-content">
        <div class="cart-box">
          <img
            src="../src/assets/trending-product.webp"
            alt=" "
            class="cart-img"
          ></img>
          <div class="cart-box__detail">
            <div class="cart-box__product-detail">Item Name</div>
            <div class="cart-box__product_price">£25.05</div>
            <div class="cart-box_quantity-control">
              <button class="cart-box_quantity-decrease">
                <img
                  class="cart-box-btn__disabled"
                  src="../src/assets/dash-square.svg"
                  alt=""
                ></img>
              </button>
              <div class="cart-box__product-quantity">1</div>
              <button class="cart-box_quantity-increase">
                <img src="../src/assets/plus-square.svg" alt=""></img>
              </button>
            </div>
          </div>
          <button class="cart-remove">
            <img src="../src/assets/trash3-fill.svg"></img>
          </button>
        </div>
      </div>
      <div class="total">
        <div class="total__title">
          <h3>Total</h3>
        </div>
        <div class="total__price">£0</div>
      </div>
      <button
        onclick="window.location.href = 'checkout.html';"
        type="button"
        class="btn-buy"
      >
        Checkout
      </button>
      <div id="cart-close">
        <button>
          <div>
            <img src="../src/assets/x.svg"></img>
          </div>
        </button>
      </div>
    </div>
  );
}

export default Cart;

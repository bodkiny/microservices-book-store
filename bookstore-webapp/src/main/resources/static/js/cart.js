document.addEventListener('alpine:init', () => {
    Alpine.data('initData', () => ({
        cart: {items: [], totalAmount: 0},
        orderForm: {
            customer: {
                name: "John Doe",
                email: "johnD@gmail.com",
                phone: "666666666"
            },
            deliveryAddress: {
                addressLine1: "UTH 33",
                addressLine2: "Park Str",
                city: "Salt Lake City",
                state: "Utah",
                zipCode: "84044",
                country: "USA"
            }
        },

        init() {
            updateCartItemCount();
            this.loadCart();
            this.cart.totalAmount = getCartTotal();
        },
        loadCart() {
            this.cart = getCart()
        },
        updateItemQuantity(code, quantity) {
            updateProductQuantity(code, quantity);
            this.loadCart();
            this.cart.totalAmount = getCartTotal();
        },
        removeCart() {
            deleteCart();
        },
        createOrder() {
            let order = Object.assign({}, this.orderForm, {items: this.cart.items});
            //console.log("Order ", order);

            $.ajax({
                url: "/api/orders",
                type: "POST",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(order),
                success: (resp) => {
                    //console.log("Order Resp:", resp)
                    this.removeCart();
                    //alert("Order placed successfully")
                    window.location = "/orders/" + resp.orderNumber;
                }, error: (err) => {
                    console.log("Order Creation Error:", err)
                    alert("Order creation failed")
                }
            });
        },
    }))
});
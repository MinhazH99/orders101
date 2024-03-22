function buildStockAvailabilityUrl(productId, qty) {
    return (
        `http://localhost:8081/products/stock-availability/${productId}?inc=false&qty=${qty}`
    );
}

export { buildStockAvailabilityUrl }
const Products = {
    handleHttpNotOk: function () {
        throw new Error('Network response was not ok');
    },
    handleError: function (err) {
        console.error('Error:', err);
        const fourPlaceholderProducts = 4;
        for (let i = 0; i < fourPlaceholderProducts; i++) {
            if ('content' in document.createElement('template')) {
                const trendingDiv = document.querySelector('#trending');
                const templateClone = document
                    .querySelector('#trending-product-template')
                    .content.cloneNode(true);
                let prodImage = templateClone.querySelector('#trending-div-product-image');
                prodImage.setAttribute('src', './assets/images/trending-product.webp');
                trendingDiv.appendChild(templateClone);
                prodImage.setAttribute('alt', 'Image of a trending product');
            }
        }
    },
    fetchData: function (url, callback) {
        window.onload = (event) => {
            fetch(url)
                .then((response) => {
                    if (!response.ok) {
                        this.handleHttpNotOk();
                    }
                    return response.json();
                })
                .then((json) => {
                    json.data.forEach((item) => callback(item));
                })
                .catch((err) => this.handleError(err));
        };
    },
};

const apiUrl = 'http://localhost:8080/products/';

Products.fetchData(apiUrl, appendProduct);

function retrieveTrendingTemplate() {}

function createProductImage(template) {
    let prodImage = template.querySelector('#trending-div-product-image');
    prodImage.setAttribute('src', './assets/images/trending-product.webp');
    prodImage.setAttribute('alt', 'Image of a trending product');
}

function createProductLabel(template, product) {
    let labelDiv = template.querySelector('#trending-div-product-label');
    labelDiv.textContent = product.name;
    let priceDiv = template.querySelector('#trending-div-product-price');
    priceDiv.textContent = product.unitPrice.toLocaleString('en-UK', {
        style: 'currency',
        currency: 'GBP',
    });
}

function appendProduct(product) {
    // Test to see if the browser supports the HTML template element by checking
    // for the presence of the template element's content attribute.
    if ('content' in document.createElement('template')) {
        const trendingDiv = document.querySelector('#trending');
        const templateClone = document
            .querySelector('#trending-product-template')
            .content.cloneNode(true);

        createProductImage(templateClone);

        createProductLabel(templateClone, product);

        trendingDiv.appendChild(templateClone);
    }
}
export { Products, apiUrl, appendProduct };

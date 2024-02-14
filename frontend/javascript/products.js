import { fetchData } from './fetch.js';

function handleProductError() {
    const fourPlaceholderProducts = 4;
    for (let i = 0; i < fourPlaceholderProducts; i++) {
        if (supportsTemplate()) {
            const trendingDiv = document.querySelector('#trending');
            const templateClone = document
                .querySelector('#trending-product-template')
                .content.cloneNode(true);
            let prodImage = templateClone.querySelector('#trending-div-product-image');
            prodImage.setAttribute('src', './assets/images/trending-product.webp');
            prodImage.setAttribute('alt', 'Image of a trending product');
            trendingDiv.appendChild(templateClone);
        }
    }
}

const apiUrl = 'http://localhost:8080/products/';

window.onload = (event) => {
    fetchData(apiUrl, appendProducts).then(appendProducts).catch(handleProductError);
};

function createProductImage(template) {
    let prodImage = template.querySelector('#trending-div-product-image');
    prodImage.setAttribute('src', './assets/images/trending-product.webp');
    prodImage.setAttribute('alt', 'Image of a trending product');
}

function createProductLabel(template, product) {
    let labelDiv = template.querySelector('#trending-div-product-label');
    labelDiv.textContent = product.name;
    let priceDiv = template.querySelector('#trending-div-product-price');
    priceDiv.textContent = formatPrice(product.unitPrice);
}

function formatPrice(product) {
    return product.toLocaleString('en-UK', {
        style: 'currency',
        currency: 'GBP',
    });
}

const supportsTemplate = () => 'content' in document.createElement('template');

// Work in progress
// function createTemplate() {
//     const trendingDiv = document.querySelector('#trending');
//     const templateClone =  document.querySelector('#trending-product-template').content.cloneNode(true);
// }

function appendProducts(products) {
    // Test to see if the browser supports the HTML template element by checking
    // for the presence of the template element's content attribute.
    if (supportsTemplate()) {
        products.forEach((product) => {
            const trendingDiv = document.querySelector('#trending');

            const templateClone = document
                .querySelector('#trending-product-template')
                .content.cloneNode(true);

            createProductImage(templateClone);

            createProductLabel(templateClone, product);

            trendingDiv.appendChild(templateClone);
        });
    }
}
export { appendProducts };

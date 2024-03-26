import { fetchData } from './fetch.js';
import { addCartItem } from './basket.js';

const apiUrl = 'http://localhost:8081/products/';
const supportsTemplate = (function () {
    return 'content' in document.createElement('template');
})(); // IIFE

fetchData(apiUrl).then(handleResponse).catch(handleProductError);

let productList = {};

function handleProductError() {
    const fourPlaceholderProducts = 4;
    for (let i = 0; i < fourPlaceholderProducts; i++) {
        if (supportsTemplate) {
            const templateClone = createTemplate();
            let prodImage = templateClone.querySelector('#trending-div-product-image');
            prodImage.setAttribute('src', './assets/images/trending-product.webp');
            prodImage.setAttribute('alt', 'Image of a trending product');
            addToProductsList(templateClone);
        }
    }
}

function handleResponse(products) {
    appendProducts(products);
    products.forEach((product) => {
        let individualProduct = {
            productName: product.name,
            productUnitPrice: product.unitPrice,
        };
        productList[product.id] = individualProduct;
    });
}

function createProductImage(template) {
    let prodImage = template.querySelector('#trending-div-product-image');
    if (doesElementExist(prodImage)) {
        // Need to test for null image once image is added to API
        prodImage.setAttribute('src', './assets/images/trending-product.webp');
        prodImage.setAttribute('alt', 'Image of a trending product');
    }
}

function createProductLabel(template, product) {
    let labelDiv = template.querySelector('#trending-div-product-label');
    if (doesElementExist(labelDiv, 'trending-div-product-label')) {
        if (typeof product.name === 'undefined' || product.name === null) {
            labelDiv.textContent = 'Error loading name...';
        } else {
            labelDiv.textContent = product.name;
        }
    }

    let priceDiv = template.querySelector('#trending-div-product-price');
    if (doesElementExist(priceDiv, '#trending-div-product-price')) {
        if (typeof product.unitPrice === 'undefined' || product.unitPrice === null) {
            priceDiv.textContent = 'Error loading price...';
        } else {
            priceDiv.textContent = formatPrice(product.unitPrice);
        }
    }
}

function formatPrice(product) {
    return product.toLocaleString('en-UK', {
        style: 'currency',
        currency: 'GBP',
    });
}

function doesElementExist(div, divName) {
    if (typeof div == 'undefined' || div === null) {
        console.error('Error: ' + divName + ' div does not exist');
        return false;
    }
    return true;
}

function createTemplate(element) {
    return document.querySelector(element).content.cloneNode(true);
}

function addToProductsList(templateClone) {
    const trendingDiv = document.querySelector('#trending');
    if (!doesElementExist(trendingDiv)) {
        return;
    }
    trendingDiv.appendChild(templateClone);
}

function appendProducts(products) {
    // Test to see if the browser supports the HTML template element by checking
    // for the presence of the template element's content attribute.
    if (supportsTemplate) {
        products.forEach((product) => {
            const templateClone = createTemplate('#trending-product-template');
            createProductImage(templateClone);
            createProductLabel(templateClone, product);
            templateClone
                .querySelector('.trending-div-product-addcart')
                .setAttribute('data-product-id', product.id);

            let productBtn = templateClone.querySelector('.trending-div-product-addcart');
            productBtn.addEventListener('click', function () {
                addCartItem(productBtn, productList);
            });
            addToProductsList(templateClone);
        });
    }
}

export { createTemplate };

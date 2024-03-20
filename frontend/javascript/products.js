import { fetchData } from './fetch.js';
import { addCartItem } from './basket.js';

function handleProductError() {
    const fourPlaceholderProducts = 4;
    for (let i = 0; i < fourPlaceholderProducts; i++) {
        if (doesBrowserSupportTemplete) {
            const templateClone = createTemplate();
            let prodImage = templateClone.querySelector('#trending-div-product-image');
            prodImage.setAttribute('src', './assets/images/trending-product.webp');
            prodImage.setAttribute('alt', 'Image of a trending product');
            addToProductsList(templateClone);
        }
    }
}

const apiUrl = 'http://localhost:8081/products/';

window.onload = (event) => {
    fetchData(apiUrl).then(handleResponse).catch(handleProductError);

    // handle response function calls appendProducts and creates a product object with keys as id and values as name,image and description
};

let productList = {};

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
    if (doesElementExists(prodImage)) {
        // Need to test for null image once image is added to API
        prodImage.setAttribute('src', './assets/images/trending-product.webp');
        prodImage.setAttribute('alt', 'Image of a trending product');
    }
}

function createProductLabel(template, product) {
    let labelDiv = template.querySelector('#trending-div-product-label');
    if (doesElementExists(labelDiv, 'trending-div-product-label')) {
        if (typeof product.name === 'undefined' || product.name === null) {
            labelDiv.textContent = 'Error loading name...';
        } else {
            labelDiv.textContent = product.name;
        }
    }

    let priceDiv = template.querySelector('#trending-div-product-price');
    if (doesElementExists(priceDiv, '#trending-div-product-price')) {
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

function doesElementExists(div, divName) {
    if (typeof div == 'undefined' || div === null) {
        console.error('Error: ' + divName + ' div does not exist');
        return false;
    }
    return true;
}

const supportsTemplate = () => 'content' in document.createElement('template');

let doesBrowserSupportTemplete = supportsTemplate();

function createTemplate(element) {
    return document.querySelector(element).content.cloneNode(true);
}

function addToProductsList(templateClone) {
    const trendingDiv = document.querySelector('#trending');
    if (!doesElementExists(trendingDiv)) {
        return;
    }
    trendingDiv.appendChild(templateClone);
}

function appendProducts(products) {
    // Test to see if the browser supports the HTML template element by checking
    // for the presence of the template element's content attribute.
    if (doesBrowserSupportTemplete) {
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

export { appendProducts, createTemplate };

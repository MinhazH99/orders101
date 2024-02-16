import { fetchData } from './fetch.js';

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

const apiUrl = 'http://localhost:8080/products/';

window.onload = (event) => {
    fetchData(apiUrl, appendProducts).then(appendProducts).catch(handleProductError);
};

function createProductImage(template) {
    let prodImage = template.querySelector('#trending-div-product-image');
    // Need to test for null image once image is added to API
    prodImage.setAttribute('src', './assets/images/trending-product.webp');
    prodImage.setAttribute('alt', 'Image of a trending product');
}

function createProductLabel(template, product) {
    let labelDiv = template.querySelector('#trending-div-product-label');
    if (product.name == null) {
        labelDiv.textContent = 'Error loading name...';
    } else {
        labelDiv.textContent = product.name;
    }

    let priceDiv = template.querySelector('#trending-div-product-price');
    if (product.unitPrice == null) {
        priceDiv.textContent = 'Error loading price...';
    } else {
        priceDiv.textContent = formatPrice(product.unitPrice);
    }
}

function formatPrice(product) {
    return product.toLocaleString('en-UK', {
        style: 'currency',
        currency: 'GBP',
    });
}

const supportsTemplate = () => 'content' in document.createElement('template');

let doesBrowserSupportTemplete = supportsTemplate();

// Work in progress
function createTemplate() {
    const trendingDiv = document.querySelector('#trending');
    return document.querySelector('#trending-product-template').content.cloneNode(true);
}

function addToProductsList(templateClone) {
    const trendingDiv = document.querySelector('#trending');
    trendingDiv.appendChild(templateClone);
}

function appendProducts(products) {
    // Test to see if the browser supports the HTML template element by checking
    // for the presence of the template element's content attribute.
    if (doesBrowserSupportTemplete) {
        products.forEach((product) => {
            const templateClone = createTemplate();

            createProductImage(templateClone);

            createProductLabel(templateClone, product);

            addToProductsList(templateClone);
        });
    }
}
export { appendProducts };

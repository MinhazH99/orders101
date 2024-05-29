export interface Product {
  id: string;
  name: string;
  unitPrice: number;
  quantity: number;
  totalCost: number;
}

export interface ShoppingCartContextType {
  cartItems: Product[];
  addToCart: (product: Product) => void;
  increaseQuantity: (id: string) => void;
  decreaseQuantity: (id: string) => void;
  removeItem: (id: string) => void;
  cartTotal: number;
}

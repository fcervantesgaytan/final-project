import Product from "./product";

export default interface OrderHistory {
  id: number | null;
  orderDate: string | null;
  product: Product | null;
}

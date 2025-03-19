CREATE TABLE sale (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    sale_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    total_value DECIMAL(10,2) NOT NULL,
    seller_id TEXT NOT NULL,
    buyer_id TEXT NOT NULL,
    CONSTRAINT fk_seller FOREIGN KEY (seller_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_buyer FOREIGN KEY (buyer_id) REFERENCES users(id) ON DELETE CASCADE

);

CREATE TABLE sale_item (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    sale_id UUID NOT NULL,
    fruit_id UUID NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    discount_percentage INT CHECK (discount_percentage IN (5, 10, 15, 20, 25)),
    subtotal DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_sale FOREIGN KEY (sale_id) REFERENCES sale(id) ON DELETE CASCADE,
    CONSTRAINT fk_fruit FOREIGN KEY (fruit_id) REFERENCES fruit(id) ON DELETE CASCADE
);
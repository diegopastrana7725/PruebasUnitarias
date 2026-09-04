-- Cupón válido
INSERT INTO coupons (
    id,
    code,
    name,
    discount_type,
    discount_value,
    max_discount,
    minimum_purchase_amount,
    category,
    active,
    expiration_date,
    max_uses_per_customer
)
VALUES (
           1,
           'WELCOME10',
           'Welcome Coupon',
           'PERCENTAGE',
           10,
           50000,
           100000,
           'TECHNOLOGY',
           TRUE,
           DATE '2030-12-31',
           3
       );

-- Cupón válido con 0 usos
INSERT INTO coupons (
    id,
    code,
    name,
    discount_type,
    discount_value,
    max_discount,
    minimum_purchase_amount,
    category,
    active,
    expiration_date,
    max_uses_per_customer
)
VALUES (
           6,
           'WELCOME0',
           'Welcome Coupon',
           'PERCENTAGE',
           10,
           50000,
           100000,
           'TECHNOLOGY',
           TRUE,
           DATE '2030-12-31',
           0
       );

-- Cupón inactivo
INSERT INTO coupons (
    id,
    code,
    name,
    discount_type,
    discount_value,
    max_discount,
    minimum_purchase_amount,
    category,
    active,
    expiration_date,
    max_uses_per_customer
)
VALUES (
           2,
           'INACTIVE10',
           'Inactive Coupon',
           'PERCENTAGE',
           10,
           50000,
           100000,
           'TECHNOLOGY',
           FALSE,
           DATE '2030-12-31',
           3
       );

-- Cupón vencido
INSERT INTO coupons (
    id,
    code,
    name,
    discount_type,
    discount_value,
    max_discount,
    minimum_purchase_amount,
    category,
    active,
    expiration_date,
    max_uses_per_customer
)
VALUES (
           3,
           'EXPIRED10',
           'Expired Coupon',
           'PERCENTAGE',
           10,
           50000,
           100000,
           'TECHNOLOGY',
           TRUE,
           DATE '2020-01-01',
           3
       );

-- Cupón descuento fijo
INSERT INTO coupons (
    id,
    code,
    name,
    discount_type,
    discount_value,
    max_discount,
    minimum_purchase_amount,
    category,
    active,
    expiration_date,
    max_uses_per_customer
)
VALUES (
           4,
           'FIXED50',
           'Fixed Discount',
           'FIXED',
           50000,
           NULL,
           50000,
           'TECHNOLOGY',
           TRUE,
           DATE '2030-12-31',
           3
       );

-- Cupón con tope de descuento
INSERT INTO coupons (
    id,
    code,
    name,
    discount_type,
    discount_value,
    max_discount,
    minimum_purchase_amount,
    category,
    active,
    expiration_date,
    max_uses_per_customer
)
VALUES (
           5,
           'TOP20',
           'Top Discount',
           'PERCENTAGE',
           20,
           30000,
           100000,
           'TECHNOLOGY',
           TRUE,
           DATE '2030-12-31',
           3
       );
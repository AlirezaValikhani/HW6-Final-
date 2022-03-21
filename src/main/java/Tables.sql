create table if not exists bank (
    id                  serial primary key,
    bank_name           varchar (50) unique not null
);


create table if not exists bank_branch (
    id                  serial primary key,
    bank_name           varchar (50),
    branch_code         varchar (10) unique not null ,
    boss_full_name      varchar (100),
    national_code       varchar (10),
    password            varchar (100),
    CONSTRAINT bank_branch_bank_bank_name_fk FOREIGN KEY (bank_name) REFERENCES bank(bank_name)
/*    CONSTRAINT bank_branch_customer_national_code_fk FOREIGN KEY (naional_code) REFERENCES customer(national_code)
*/    );


create table if not exists employee (
    id                  serial primary key,
    full_name           varchar (100),
    national_code       varchar (10) unique not null ,
    branch_code         varchar (10),
    password            varchar (100),
    user_type           varchar (50),
    CONSTRAINT employee_bank_branch_branch_code_fk FOREIGN KEY (branch_code) REFERENCES bank_branch(branch_code)
    );


create table if not exists customer (
    id                  serial primary key,
    full_name           varchar (100),
    national_code       varchar (10) unique not null ,
    branch_code         varchar (10),
    password            varchar (100),
    user_type           varchar (50),
    CONSTRAINT customer_bank_branch_branch_code_fk FOREIGN KEY (branch_code) REFERENCES bank_branch(branch_code)
    );


create table if not exists account (
    id                  serial primary key,
    branch_code         varchar (10),
    national_code       varchar (10)  ,
    account_number      varchar (8) unique not null ,
    balance             double precision ,
    account_type        varchar (50),
    CONSTRAINT account_customer_national_code_fk FOREIGN KEY (national_code) REFERENCES customer(national_code),
    CONSTRAINT account_bank_branch_branch_code_fk FOREIGN KEY (branch_code) REFERENCES bank_branch(branch_code)
    );


create table if not exists credit_card (
    id                  serial primary key,
    account_number      varchar (8),
    cvv2                varchar (4),
    password            varchar (100),
    expire_date          varchar (20),
    account_type        varchar (50),
    CONSTRAINT credit_card_account_account_number_fk FOREIGN KEY (account_number) REFERENCES account(account_number)
    );
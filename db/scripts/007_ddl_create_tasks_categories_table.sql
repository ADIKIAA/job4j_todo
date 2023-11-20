create table tasks_categories
(
    id             serial primary key,
    task_id        int references tasks(id),
    category_id    int references categories(id),
    unique (task_id, category_id)
);
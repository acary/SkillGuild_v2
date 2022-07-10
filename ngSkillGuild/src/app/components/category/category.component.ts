import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from 'src/app/models/category';
import { CategoryService } from 'src/app/services/category.service';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {

  categories: Category[] = [];

  newCategory: Category = new Category();

  selected: Category | null = null;

  showAllCategories: boolean = true;

  createForm: boolean = false;

  createCategoryForm: any;

  isEditing = false;

  editCategory: Category | null = null;

  constructor(
    private categorySvc: CategoryService,
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.createFormInit(fb);
  }

  ngOnInit(): void {
    this.getAllCategories();
  }
  getAllCategories() {
    this.categorySvc.index().subscribe(categories => {
      this.categories = categories;
    });
  }

  createCategory(category: Category): void {
    this.categorySvc.create(category).subscribe({
      next: category => {
        console.log("Created successfully: " + category.id);
        this.categories.push(category);
        this.newCategory = new Category();
        this.toggleAllCategories();
        this.getAllCategories();
      },
      error: (err) => {
        console.error('Error creating category: ', err);
      }
    })
  }

  setEditCategory = () => {
    this.editCategory = Object.assign({}, this.selected);
  }

  cancelEdit = () => {
    this.editCategory = null;
  }

  updateCategory = (category: Category) => {
    this.categorySvc.update(category).subscribe(
      {
        next: () => {
          console.log("Updated category successfully: " + category.id);
          this.selected = null;
          this.editCategory = null;
          this.displayAll();
          this.getAllCategories();
        },
        error: (err: any) => console.error('Error updating category: ', err)
      }
    );
  }

  displayAll(): void {
    this.selected = null;
  }

  getCategoryById(id: number) {
    this.categorySvc.show(id).subscribe(
      category => {
        return category;
      },
      err => {
        console.log(err);
      }
    )
  }

  deleteCategory(id: number) {
    this.categorySvc.delete(id).subscribe({
      next: (data) => {
        console.log("Deleted successfully: " + id);
        this.displayAll()
        this.getAllCategories()
      },
      error: (err) => console.error(err)
    });
  }

  toggleAllCategories = () => {
    this.showAllCategories = !this.showAllCategories;
    this.createForm = !this.createForm;
  }

  showCreateForm = () => {
    this.createForm = !this.createForm;
    this.showAllCategories = !this.showAllCategories;
  }

  createFormInit(fb: FormBuilder) {
    this.createCategoryForm = this.fb.group({
      id: [0],
      name: [''],
      description: [''],
      imgUrl: ['https://via.placeholder.com/150']
    });
    this.createCategoryForm.updateValueAndValidity();
    this.isEditing = true;
  }

  sendNewCategory() {
    let category: Category = {
      id: this.createCategoryForm.get('id').value,
      name: this.createCategoryForm.get('name').value,
      description: this.createCategoryForm.get('description').value,
      imgUrl: this.createCategoryForm.get('imgUrl').value
    }
    this.isEditing = false;
    console.log(category);
    this.createCategory(category);
  }
}
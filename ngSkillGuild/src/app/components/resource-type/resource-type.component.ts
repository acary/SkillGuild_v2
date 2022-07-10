import { ResourceTypeService } from './../../services/resource-type.service';
import { Component, OnInit } from '@angular/core';
import { ResourceType } from 'src/app/models/resource-type';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-resource-type',
  templateUrl: './resource-type.component.html',
  styleUrls: ['./resource-type.component.css']
})
export class ResourceTypeComponent implements OnInit {

  resourceTypes: ResourceType[] = [];

  newResourceType: ResourceType = new ResourceType();

  selected: ResourceType | null = null;

  showAllResourceTypes: boolean = true;

  createForm: boolean = false;

  createResourceTypeForm: any;

  isEditing = false;

  editResourceType: ResourceType | null = null;

  constructor(
    private resourceTypeSvc: ResourceTypeService,
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.createFormInit(fb);
  }

  ngOnInit(): void {
    this.getAllResourceTypes();
  }
  getAllResourceTypes() {
    this.resourceTypeSvc.index().subscribe(resourceTypes => {
      this.resourceTypes = resourceTypes;
    });
  }

  createResourceType(resourceType: ResourceType): void {
    this.resourceTypeSvc.create(resourceType).subscribe({
      next: resourceType => {
        console.log("Created successfully: " + resourceType.id);
        this.resourceTypes.push(resourceType);
        this.newResourceType = new ResourceType();
        this.toggleAllResourceTypes();
        this.getAllResourceTypes();
      },
      error: (err) => {
        console.error('Error creating resourceType: ', err);
      }
    })
  }

  setEditResourceType = () => {
    this.editResourceType = Object.assign({}, this.selected);
  }

  cancelEdit = () => {
    this.editResourceType = null;
  }

  updateResourceType = (resourceType: ResourceType) => {
    this.resourceTypeSvc.update(resourceType).subscribe(
      {
        next: () => {
          console.log("Updated resourceType successfully: " + resourceType.id);
          this.selected = null;
          this.editResourceType = null;
          this.displayAll();
          this.getAllResourceTypes();
        },
        error: (err: any) => console.error('Error updating resourceType: ', err)
      }
    );
  }

  displayAll(): void {
    this.selected = null;
  }

  getResourceTypeById(id: number) {
    this.resourceTypeSvc.show(id).subscribe(
      resourceType => {
        return resourceType;
      },
      err => {
        console.log(err);
      }
    )
  }

  deleteResourceType(id: number) {
    this.resourceTypeSvc.delete(id).subscribe({
      next: (data) => {
        console.log("Deleted successfully: " + id);
        this.displayAll()
        this.getAllResourceTypes()
      },
      error: (err) => console.error(err)
    });
  }

  toggleAllResourceTypes = () => {
    this.showAllResourceTypes = !this.showAllResourceTypes;
    this.createForm = !this.createForm;
  }

  showCreateForm = () => {
    this.createForm = !this.createForm;
    this.showAllResourceTypes = !this.showAllResourceTypes;
  }

  createFormInit(fb: FormBuilder) {
    this.createResourceTypeForm = this.fb.group({
      id: [0],
      name: [''],
      description: [''],
    });
    this.createResourceTypeForm.updateValueAndValidity();
    this.isEditing = true;
  }

  sendNewResourceType() {
    let resourceType: ResourceType = {
      id: this.createResourceTypeForm.get('id').value,
      name: this.createResourceTypeForm.get('name').value,
      description: this.createResourceTypeForm.get('description').value,
    }
    this.isEditing = false;
    console.log(resourceType);
    this.createResourceType(resourceType);
  }
}

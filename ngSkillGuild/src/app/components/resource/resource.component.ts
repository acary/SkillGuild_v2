import { ResourceService } from './../../services/resource.service';
import { Resource } from './../../models/resource';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-resource',
  templateUrl: './resource.component.html',
  styleUrls: ['./resource.component.css']
})
export class ResourceComponent implements OnInit {

  resources: Resource[] = [];

  newResource: Resource = new Resource();

  selected: Resource | null = null;

  showAllResources: boolean = true;

  createForm: boolean = false;

  createResourceForm: any;

  isEditing = false;

  editResource: Resource | null = null;

  constructor(
    private resourceSvc: ResourceService,
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.createFormInit(fb);
  }

  ngOnInit(): void {
    this.getAllResources();
  }

  getAllResources() {
    this.resourceSvc.index().subscribe(resources => {
      this.resources = resources;
    });
  }

  createResource(resource: Resource): void {
    this.resourceSvc.create(resource).subscribe({
      next: resource => {
        console.log("Created successfully: " + resource.id);
        this.resources.push(resource);
        this.newResource = new Resource();
        this.toggleAllResources();
        this.getAllResources();
      },
      error: (err) => {
        console.error('Error creating resource: ', err);
      }
    })
  }

  setEditResource = () => {
    this.editResource = Object.assign({}, this.selected);
  }

  cancelEdit = () => {
    this.editResource = null;
  }

  updateResource = (resource: Resource) => {
    this.resourceSvc.update(resource).subscribe(
      {
        next: () => {
          console.log("Updated resource successfully: " + resource.id);
          this.selected = null;
          this.editResource = null;
          this.displayAll();
          this.getAllResources();
        },
        error: (err: any) => console.error('Error updating resource: ', err)
      }
    );
  }

  displayAll(): void {
    this.selected = null;
  }

  getResourceById(id: number) {
    this.resourceSvc.show(id).subscribe(
      resource => {
        return resource;
      },
      err => {
        console.log(err);
      }
    )
  }

  deleteResource(id: number) {
    this.resourceSvc.delete(id).subscribe({
      next: (data) => {
        console.log("Deleted successfully: " + id);
        this.displayAll()
        this.getAllResources()
      },
      error: (err) => console.error(err)
    });
  }

  toggleAllResources = () => {
    this.showAllResources = !this.showAllResources;
    this.createForm = !this.createForm;
  }

  showCreateForm = () => {
    this.createForm = !this.createForm;
    this.showAllResources = !this.showAllResources;
  }

  createFormInit(fb: FormBuilder) {
    this.createResourceForm = this.fb.group({
      id: [100],
      title: [''],
      description: [''],
      resourceUrl: [''],
      resourceType: ['']
    });
    this.createResourceForm.updateValueAndValidity();
    this.isEditing = true;
  }

  sendNewResource() {
    let resource: Resource = {
      id: this.createResourceForm.get('id').value,
      title: this.createResourceForm.get('title').value,
      description: this.createResourceForm.get('description').value,
      resourceUrl: this.createResourceForm.get('resourceUrl').value,
      resourceType: this.createResourceForm.get('resourceType').value
    }
    this.isEditing = false;
    console.log(resource);
    this.createResource(resource);
  }

}

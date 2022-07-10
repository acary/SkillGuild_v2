import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Status } from 'src/app/models/status';
import { StatusService } from 'src/app/services/status.service';

@Component({
  selector: 'app-statuses',
  templateUrl: './statuses.component.html',
  styleUrls: ['./statuses.component.css']
})
export class StatusesComponent implements OnInit {

  statuses: Status[] = [];

  selected: Status | null = null;

  showAllStatuses: boolean = true;

  newStatus: Status = new Status();

  createForm: boolean = false;

  createStatusForm: any;

  isEditing = false;

  editStatus: Status | null = null;

  constructor(
    private statusSvc: StatusService,
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.createFormInit(fb);
  }

  ngOnInit(): void {
    this.getAllStatuses();

  }
  getAllStatuses() {
    this.statusSvc.index().subscribe(statuses => {
      this.statuses = statuses;
    });
  }

  createStatus(contentStatus: Status): void {
    this.statusSvc.create(contentStatus).subscribe({
      next: contentStatus => {
        console.log("Created successfully: " + contentStatus.id);
        this.statuses.push(contentStatus);
        this.newStatus = new Status();
        this.toggleAllStatuses();
        this.getAllStatuses();
      },
      error: (err) => {
        console.error('Error creating status: ', err);
      }
    })
  }

  setEditStatus = () => {
    this.editStatus = Object.assign({}, this.selected);
  }

  cancelEdit = () => {
    this.editStatus = null;
  }

  updateStatus = (contentStatus: Status) => {
  this.statusSvc.update(contentStatus).subscribe(
    { 
    next: () => {
      console.log("Updated contentStatus successfully: " + contentStatus.id);
      this.selected = null;
      this.editStatus = null;
      this.displayAll();
      this.getAllStatuses();
    },
    error: (err: any) => console.error('Error updating contentStatus: ', err)
  }
  );
}

  displayAll(): void {
    this.selected = null;
  }

  getStatusById(id: number) {
    this.statusSvc.show(id).subscribe(
      contentStatus => {
        return contentStatus;
      },
      err => {
        console.log(err);
      }
    )
  }

  deleteStatus(id: number) {
    this.statusSvc.delete(id).subscribe({
      next: (data) => {
        console.log("Deleted successfully: " + id);
        this.displayAll()
        this.getAllStatuses()
      },
      error: (err) => console.error(err)
    });
  }

  toggleAllStatuses = () => {
    this.showAllStatuses = !this.showAllStatuses;
    this.createForm = !this.createForm;
  }

  showCreateForm = () => {
    this.createForm = !this.createForm;
    this.showAllStatuses = !this.showAllStatuses;
  }

  createFormInit(fb: FormBuilder) {
    this.createStatusForm = this.fb.group({
      id: [0],
      name: ['']
    });
    this.createStatusForm.updateValueAndValidity();
    this.isEditing = true;
  }

  sendNewStatus() {
    let contentStatus: Status = {
      id: this.createStatusForm.get('id').value,
      name: this.createStatusForm.get('name').value,
    }
    this.isEditing = false;
    console.log(contentStatus);
    this.createStatus(contentStatus);
  }

}
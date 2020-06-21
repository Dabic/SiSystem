export class Role {
  public imagePath: string;
  public roleName: string;
  public marked: boolean;

  constructor(imagePath: string, marked: boolean, roleName: string) {
    this.imagePath = imagePath;
    this.roleName = roleName;
    this.marked = marked;
  }
}

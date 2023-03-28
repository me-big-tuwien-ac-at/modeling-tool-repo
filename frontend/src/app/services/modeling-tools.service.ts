import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ModelingTool, ModelingToolSearch} from '../dtos/modeling-tools';
import {environment} from '../../environments/environment';
import {ModelingToolAndPropertiesAndEdit, ModelingToolsAndProperties} from '../dtos/modeling-tools-and-properties';

@Injectable({
  providedIn: 'root'
})
export class ModelingToolsService {
  constructor(
    private http: HttpClient
  ) { }

  /**
   * Requests a list of all modeling tools stored in the system.
   *
   * @return an observable of all modeling tools
   */
  public getModelingTools(): Observable<ModelingTool[]> {
    return this.http.get<ModelingTool[]>(environment.modelingToolUrl);
  }

  /**
   * Retrieves all modeling tools which match the search query by the user.
   *
   * @param modelingToolSearch object containing all search parameters.
   */
  public filterModelingTools(modelingToolSearch: ModelingToolSearch): Observable<ModelingTool[]> {
    let httpStr = `${environment.modelingToolUrl}?`;
    let key: keyof typeof modelingToolSearch;
    for (key in modelingToolSearch) {
      const value = modelingToolSearch[key];
      if (value !== undefined && value !== null) {
        if (!(value instanceof String || Array.isArray(value)) || value.length !== 0) {
          httpStr += `${key}=${value.toString()}&`;
        }
      }
    }
    httpStr = httpStr.substring(0, httpStr.length - 1);
    return this.http.get<ModelingTool[]>(httpStr);
  }

  /**
   * Retrieves all modeling tools with a matching name.
   *
   * @param name modeling tool name
   */
  public searchModelingToolByName(name: string): Observable<ModelingTool[]> {
    return this.http.get<ModelingTool[]>(`${environment.modelingToolUrl}?name=${name}`);
  }

  /**
   * Requests a list of modeling tools, modeling languages, platforms and programming languages in the system.
   *
   * @return an observable of type ModelingToolsAndProperties
   */
  public getModelingToolsAndProperties(): Observable<ModelingToolsAndProperties> {
    return this.http.get<ModelingToolsAndProperties>(environment.modelingToolsAndPropertiesUrl);
  }

  /**
   * Requests a list of modeling tools, modeling tool with a matching id, modeling languages, platforms and
   * programming languages in the system.
   *
   * @param id ID of the modeling tool
   * @return an observable of type ModelingToolAndPropertiesAndEdit
   */
  public getModelingToolsAndModelingToolById(id: number): Observable<ModelingToolAndPropertiesAndEdit> {
    return this.http.get<ModelingToolAndPropertiesAndEdit>(`${environment.modelingToolAndPropertiesAndEdit}/${id}`);
  }

  /**
   * Requests a modeling tool with a corresponding ID.
   *
   * @param id ID of the modeling tool
   * @return an observable modeling tool
   */
  public getModelingTool(id: number): Observable<ModelingTool> {
    return this.http.get<ModelingTool>(`${environment.modelingToolUrl}/id`);
  }

  /**
   * Posts a new modeling tool to the system.
   *
   * @param tool Modeling Tool expected to be stored
   * @return an observable modeling tool
   */
  public sendModelingTool(tool: ModelingTool): Observable<ModelingTool> {
    return this.http.post<ModelingTool>(environment.modelingToolUrl, tool);
  }

  /**
   * Sends an edit suggestion for an existing modeling tool
   *
   * @param tool Modeling Tool edit
   * @param id an observable modeling tool
   */
  public sendModelingToolEdit(tool: ModelingTool, id: number): Observable<ModelingTool> {
    return this.http.put<ModelingTool>(`${environment.modelingToolUrl}/${id}`, tool)
  }
}
